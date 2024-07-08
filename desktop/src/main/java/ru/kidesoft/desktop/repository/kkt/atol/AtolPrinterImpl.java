package ru.kidesoft.desktop.repository.kkt.atol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.atol.drivers10.fptr.IFptr;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktPrinter;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.Ticket;
import ru.kidesoft.desktop.domain.exception.KktException;

@Repository
public class AtolPrinterImpl implements KktPrinter {
    AtolRepositoryImpl kktRepository;

    @Autowired
    public AtolPrinterImpl(AtolRepositoryImpl kktRepository) {
        this.kktRepository = kktRepository;
    }

    @Override
    public void cancelReceipt() throws KktException {
        kktRepository.checkErrorCode(kktRepository.getFptr().cancelReceipt());
    }

    @Override
    public void print(Order order) throws KktException {
        switch(order.getOperationType()) {
            case ORDER:
                kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL);
                break;
            case REFUND:
                kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL_RETURN);
                break;
            default:
                throw new IllegalArgumentException("Необрабатываемая форма заказа");
        }

        int code = kktRepository.getFptr().openReceipt();

        kktRepository.checkErrorCode(code);

        if (order.getOperationType() == OperationType.REFUND) {
            kktRepository.getFptr().setParam(1212, 4);
            kktRepository.getFptr().setParam(1214, 4);
            kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_TEXT, "Возврат");
            kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_ALIGNMENT, IFptr.LIBFPTR_ALIGNMENT_CENTER);
            kktRepository.checkErrorAndCancel(kktRepository.getFptr().printText());
        }

        for (Ticket t : order.getTickets()) {
            if (t.getAmount() > 0) {
                kktRepository.checkErrorAndCancel(positionRegister(t));
            }
        }

        double total = order.getTickets().stream().mapToDouble(Ticket::getAmount).sum();


        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_SUM, total);

        kktRepository.checkErrorAndCancel(kktRepository.getFptr().receiptTotal());

        switch(order.getPaymentType()) {
            case CASH:
                kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_CASH);
                break;
            case CARD, ACCOUNT_INDIVIDUAL:
                kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_ELECTRONICALLY);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип оплаты");
        }

        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_PAYMENT_SUM, total);

        kktRepository.checkErrorAndCancel(kktRepository.getFptr().payment());

        kktRepository.checkErrorAndCancel(kktRepository.getFptr().closeReceipt());

        boolean closed = false;

        for (int i = 0; i < 5; i++) {
            if (kktRepository.getFptr().checkDocumentClosed() == 0) {
                closed = true;
                break;
            } else {
                try {
                    Thread.sleep(500); // ? Возможно, стоит исправить
                } catch (InterruptedException ignored) {}
            }
        }

        if (!closed) {
            throw new KktException(kktRepository.getFptr().errorDescription(), kktRepository.getFptr().errorCode());
        }

        if (!kktRepository.getFptr().getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_CLOSED)) {
            kktRepository.checkErrorCode(kktRepository.getFptr().cancelReceipt());
        }

        if (!kktRepository.getFptr().getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_PRINTED)) {
            while(true) {
                if (kktRepository.getFptr().continuePrint() == 0) {
                    break;
                }
            }
        }

        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_FN_DATA_TYPE, IFptr.LIBFPTR_FNDT_LAST_DOCUMENT);

        kktRepository.checkErrorAndCancel(kktRepository.getFptr().fnQueryData());
    }

    @Override
    public void printLastReceipt() throws KktException {
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_LAST_DOCUMENT);
        kktRepository.checkErrorCode(kktRepository.getFptr().report());
    }

    @Override
    public void income(float income) throws KktException {
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_SUM, income);
        kktRepository.checkErrorCode(kktRepository.getFptr().cashIncome());
    }

    @Override
    public void printXReport() throws KktException {
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_X);
        kktRepository.checkErrorCode(kktRepository.getFptr().report());
    }

    @Override
    public void openShift() throws KktException {
        kktRepository.checkErrorCode(kktRepository.getFptr().openShift());
    }

    @Override
    public void closeShift() throws KktException {
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_CLOSE_SHIFT);
        kktRepository.checkErrorCode(kktRepository.getFptr().report()); // TODO: Проверка на ошибку
        kktRepository.checkErrorCode(kktRepository.getFptr().checkDocumentClosed()); // TODO: Проверка на ошибку
    }

    private int positionRegister(Ticket ticket) throws KktException {
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_COMMODITY_NAME, String.format(
                "%s,%s,%s,%s,%s,%d,%d", ticket.getNumber(), ticket.getShowName(), ticket.getAgeLimit(), ticket.getDateTime(), ticket.getZone(), ticket.getRowSector(), ticket.getSeatNumber()));
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_PRICE, ticket.getAmount());
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_QUANTITY, 1);
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_TAX_TYPE, IFptr.LIBFPTR_TAX_NO);

        kktRepository.getFptr().setParam(1212, 4);
        kktRepository.getFptr().setParam(1214, 4);

        return kktRepository.getFptr().registration();
    }
}
