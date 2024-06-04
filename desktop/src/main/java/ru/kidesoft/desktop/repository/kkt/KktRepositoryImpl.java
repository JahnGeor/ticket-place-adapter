package ru.kidesoft.desktop.repository.kkt;

import org.springframework.stereotype.Repository;
import ru.atol.drivers10.fptr.Fptr;
import ru.atol.drivers10.fptr.IFptr;
import ru.kidesoft.desktop.domain.dao.kkt.KktOperator;
import ru.kidesoft.desktop.domain.dao.kkt.KktRepository;
import ru.kidesoft.desktop.domain.dao.kkt.KktSetting;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.Ticket;
import ru.kidesoft.desktop.domain.exception.KktException;

import java.io.File;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Date;

@Repository("KktRepository")
public class KktRepositoryImpl implements KktRepository {

    private IFptr fptr;

    public KktRepositoryImpl() {
//        this.fptr = new Fptr();
    }

    @Override
    public KktRepository setConnection(KktSetting kktSetting) throws KktException {
        File driverDir = new File(kktSetting.getPath());

        if (!(driverDir.isDirectory() && driverDir.exists())) {
            throw new KktException("Invalid driver path");
        }

        URI uriDriverPath = driverDir.toURI().resolve(getOSDirectory());

        if (fptr != null) {
            fptr.destroy();
        }

        fptr = new Fptr(uriDriverPath.getPath());
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_AUTO_RECONNECT, kktSetting.getAutoReconnect().toString());
        fptr.applySingleSettings();
        return this;
    }

    public boolean isConnectionOpened() {
        if (fptr == null) {
            return false;
        }

        fptr.setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS);

        if (fptr.queryData() != IFptr.LIBFPTR_OK) {
            return false;
        }

        return fptr.isOpened();
    }

    @Override
    public KktRepository setOperator(KktOperator kktOperator) throws KktException {
        fptr.setParam(1021, kktOperator.getFullName());
        fptr.setParam(1203, kktOperator.getInn().toString());

        checkErrorCode(fptr.operatorLogin());
        return this;
    }

    @Override
    public void openShift() throws KktException {
        checkErrorCode(fptr.openShift());
    }

    @Override
    public void closeShift() throws KktException {
        fptr.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_CLOSE_SHIFT);
        checkErrorCode(fptr.report()); // TODO: Проверка на ошибку
        checkErrorCode(fptr.checkDocumentClosed()); // TODO: Проверка на ошибку
    }

    @Override
    public KktRepository openConnection() throws KktException {
        fptr.open();
        return this;
    }

    @Override
    public KktRepository closeConnection() throws KktException {
        fptr.close();

        return this;
    }

    private int positionRegister(Ticket ticket) {
        fptr.setParam(IFptr.LIBFPTR_PARAM_COMMODITY_NAME, String.format(
                "%s,%s,%s,%s,%s,%d,%d", ticket.getNumber(), ticket.getShowName(), ticket.getAgeLimit(), ticket.getDateTime(), ticket.getZone(), ticket.getRowSector(), ticket.getSeatNumber()));
        fptr.setParam(IFptr.LIBFPTR_PARAM_PRICE, ticket.getAmount());
        fptr.setParam(IFptr.LIBFPTR_PARAM_QUANTITY, 1);
        fptr.setParam(IFptr.LIBFPTR_PARAM_TAX_TYPE, IFptr.LIBFPTR_TAX_NO);

        fptr.setParam(1212, 4);
        fptr.setParam(1214, 4);

        return fptr.registration();
    }

    @Override
    public void print(Order order, OperationType operationType) throws KktException, InterruptedException {
        switch(operationType) {
            case ORDER:
                fptr.setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL);
                break;
            case REFUND:
                fptr.setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL_RETURN);
                break;
            default:
                throw new IllegalArgumentException("Необрабатываемая форма заказа");
        }

        int code = fptr.openReceipt();

        if (code != 0) {
            checkErrorCode(fptr.closeReceipt());
            checkErrorCode(fptr.openReceipt());
        }

        if (operationType == OperationType.REFUND) {
            fptr.setParam(1212, 4);
            fptr.setParam(1214, 4);
            fptr.setParam(IFptr.LIBFPTR_PARAM_TEXT, "Возврат");
            fptr.setParam(IFptr.LIBFPTR_PARAM_ALIGNMENT, IFptr.LIBFPTR_ALIGNMENT_CENTER);
            checkErrorAndCancel(fptr.printText());
        }

        for (Ticket t : order.getTickets()) {
            if (t.getAmount() > 0) {
                checkErrorAndCancel(positionRegister(t));
            }
        }

        double total = order.getTickets().stream().mapToDouble(Ticket::getAmount).sum();


        fptr.setParam(IFptr.LIBFPTR_PARAM_SUM, total);

        checkErrorAndCancel(fptr.receiptTotal());

        switch(order.getPaymentType()) {
            case CASH:
                fptr.setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_CASH);
                break;
            case CARD, ACCOUNT_INDIVIDUAL:
                fptr.setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_ELECTRONICALLY);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип оплаты");
        }

        fptr.setParam(IFptr.LIBFPTR_PARAM_PAYMENT_SUM, total);

        checkErrorAndCancel(fptr.payment());

        checkErrorAndCancel(fptr.closeReceipt());

        boolean closed = false;

        for (int i = 0; i < 5; i++) {
            if (fptr.checkDocumentClosed() == 0) {
                closed = true;
                break;
            } else {
                Thread.sleep(500); // ? Возможно, стоит исправить
            }
        }

        if (!closed) {
            throw new KktException(fptr.errorDescription(), fptr.errorCode());
        }

        if (!fptr.getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_CLOSED)) {
            checkErrorCode(fptr.cancelReceipt());
        }

        if (!fptr.getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_PRINTED)) {
            while(true) {
                if (fptr.continuePrint() == 0) {
                    break;
                }
            }
        }

        fptr.setParam(IFptr.LIBFPTR_PARAM_FN_DATA_TYPE, IFptr.LIBFPTR_FNDT_LAST_DOCUMENT);

        checkErrorAndCancel(fptr.fnQueryData());
    }

    @Override
    public void printLastReceipt() throws KktException {
        fptr.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_LAST_DOCUMENT);
        checkErrorCode(fptr.report());

    }

    @Override
    public void income(float income) throws KktException {
        fptr.setParam(IFptr.LIBFPTR_PARAM_SUM, income);
        checkErrorCode(fptr.cashIncome());
    }

    @Override
    public void printXReport() throws KktException {
        fptr.setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_X);
        checkErrorCode(fptr.report());
    }

    @Override
    public KktRepository setCurrentTime(ZonedDateTime zonedDateTime) throws KktException {
        fptr.setParam(IFptr.LIBFPTR_PARAM_DATE_TIME, Date.from(zonedDateTime.toInstant()));
        return this;
    }

    @Override
    public State getCurrentShiftState() throws KktException {
        if (fptr == null) {
            return State.UNDEFINED;
        }

        fptr.setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS);
        fptr.queryData();

        long state = fptr.getParamInt(IFptr.LIBFPTR_PARAM_SHIFT_STATE);

        return switch ((int) state) {
            case IFptr.LIBFPTR_SS_CLOSED -> State.CLOSED;
            case IFptr.LIBFPTR_SS_OPENED -> State.OPENED;
            case IFptr.LIBFPTR_SS_EXPIRED -> State.EXPIRED;
            default -> State.UNDEFINED;
        };
    }

    public String getOSDirectory() {
        String osArch = System.getProperty("os.arch");

        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            if (osArch.equals("x86")) {
                return "nt-x86-msvc2015";
            } else if (osArch.equals("amd64")) {
                return "nt-x64-msvc2015";
            }
        } else if (osName.startsWith("Mac")) {
            return "macos-x86_64";
        } else if (osName.startsWith("Linux")) {
            if (osArch.equals("x86")) {
                return "linux-x86";
            } else if (osArch.equals("x86_64")) {
                return "linux-x64";
            }
        }

        throw new IllegalArgumentException("Unsupported OS: " + osName);
    }

    private void checkErrorCode(int code) throws KktException {
        if (code != 0) {
            throw new KktException(fptr.errorDescription(), fptr.errorCode());
        }
    }

    private void checkErrorAndCancel(int code) throws KktException {
        if (code != 0) {
            checkErrorCode(fptr.cancelReceipt());
            throw new KktException(fptr.errorDescription(), fptr.errorCode());
        }
    }

}
