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

    public IFptr getFptr() throws KktException {
        if (fptr == null) {
            throw new KktException("KKT driver not initialized");
        }
        return fptr;
    }


    @Override
    public KktRepository setConnection(KktSetting kktSetting) throws KktException {
        File driverDir = new File(kktSetting.getPath());

        if (!(driverDir.isDirectory() && driverDir.exists())) {
            throw new KktException("По выбранному пути не обнаружена директория");
        }

        URI uriDriverPath = driverDir.toURI().resolve(getOSDirectory());

        if (fptr != null) {
            fptr.destroy();
        }

        try {
            var driverDirPath = uriDriverPath.getPath();
            fptr = new Fptr(driverDirPath);
        } catch (Throwable e) {
            throw new KktException("Не удалось загрузить драйвер ККТ", e);
        }
        fptr.setSingleSetting(IFptr.LIBFPTR_SETTING_AUTO_RECONNECT, kktSetting.getAutoReconnect().toString());
        fptr.applySingleSettings();
        return this;
    }

    public boolean isConnectionOpened() throws KktException {
        getFptr().setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS);

        if (fptr.queryData() != IFptr.LIBFPTR_OK) {
            return false;
        }

        return fptr.isOpened();
    }

    @Override
    public KktRepository setOperator(KktOperator kktOperator) throws KktException {
        getFptr().setParam(1021, kktOperator.getFullName());
        getFptr().setParam(1203, kktOperator.getInn().toString());

        checkErrorCode(getFptr().operatorLogin());
        return this;
    }

    @Override
    public void openShift() throws KktException {
        checkErrorCode(getFptr().openShift());
    }

    @Override
    public void closeShift() throws KktException {
        getFptr().setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_CLOSE_SHIFT);
        checkErrorCode(getFptr().report()); // TODO: Проверка на ошибку
        checkErrorCode(getFptr().checkDocumentClosed()); // TODO: Проверка на ошибку
    }

    @Override
    public KktRepository openConnection() throws KktException {
        getFptr().open();
        return this;
    }

    @Override
    public KktRepository closeConnection() throws KktException {
        getFptr().close();

        return this;
    }

    private int positionRegister(Ticket ticket) throws KktException {
        getFptr().setParam(IFptr.LIBFPTR_PARAM_COMMODITY_NAME, String.format(
                "%s,%s,%s,%s,%s,%d,%d", ticket.getNumber(), ticket.getShowName(), ticket.getAgeLimit(), ticket.getDateTime(), ticket.getZone(), ticket.getRowSector(), ticket.getSeatNumber()));
        getFptr().setParam(IFptr.LIBFPTR_PARAM_PRICE, ticket.getAmount());
        getFptr().setParam(IFptr.LIBFPTR_PARAM_QUANTITY, 1);
        getFptr().setParam(IFptr.LIBFPTR_PARAM_TAX_TYPE, IFptr.LIBFPTR_TAX_NO);

        getFptr().setParam(1212, 4);
        getFptr().setParam(1214, 4);

        return getFptr().registration();
    }

    @Override
    public void print(Order order, OperationType operationType) throws KktException {
        switch(operationType) {
            case ORDER:
                getFptr().setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL);
                break;
            case REFUND:
                getFptr().setParam(IFptr.LIBFPTR_PARAM_RECEIPT_TYPE, IFptr.LIBFPTR_RT_SELL_RETURN);
                break;
            default:
                throw new IllegalArgumentException("Необрабатываемая форма заказа");
        }

        int code = getFptr().openReceipt();

        checkErrorCode(code);

        if (operationType == OperationType.REFUND) {
            getFptr().setParam(1212, 4);
            getFptr().setParam(1214, 4);
            getFptr().setParam(IFptr.LIBFPTR_PARAM_TEXT, "Возврат");
            getFptr().setParam(IFptr.LIBFPTR_PARAM_ALIGNMENT, IFptr.LIBFPTR_ALIGNMENT_CENTER);
            checkErrorAndCancel(getFptr().printText());
        }

        for (Ticket t : order.getTickets()) {
            if (t.getAmount() > 0) {
                checkErrorAndCancel(positionRegister(t));
            }
        }

        double total = order.getTickets().stream().mapToDouble(Ticket::getAmount).sum();


        getFptr().setParam(IFptr.LIBFPTR_PARAM_SUM, total);

        checkErrorAndCancel(getFptr().receiptTotal());

        switch(order.getPaymentType()) {
            case CASH:
                getFptr().setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_CASH);
                break;
            case CARD, ACCOUNT_INDIVIDUAL:
                getFptr().setParam(IFptr.LIBFPTR_PARAM_PAYMENT_TYPE, IFptr.LIBFPTR_PT_ELECTRONICALLY);
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип оплаты");
        }

        getFptr().setParam(IFptr.LIBFPTR_PARAM_PAYMENT_SUM, total);

        checkErrorAndCancel(getFptr().payment());

        checkErrorAndCancel(getFptr().closeReceipt());

        boolean closed = false;

        for (int i = 0; i < 5; i++) {
            if (getFptr().checkDocumentClosed() == 0) {
                closed = true;
                break;
            } else {
                try {
                    Thread.sleep(500); // ? Возможно, стоит исправить
                } catch (InterruptedException ignored) {}
            }
        }

        if (!closed) {
            throw new KktException(getFptr().errorDescription(), getFptr().errorCode());
        }

        if (!getFptr().getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_CLOSED)) {
            checkErrorCode(getFptr().cancelReceipt());
        }

        if (!getFptr().getParamBool(IFptr.LIBFPTR_PARAM_DOCUMENT_PRINTED)) {
            while(true) {
                if (getFptr().continuePrint() == 0) {
                    break;
                }
            }
        }

        getFptr().setParam(IFptr.LIBFPTR_PARAM_FN_DATA_TYPE, IFptr.LIBFPTR_FNDT_LAST_DOCUMENT);

        checkErrorAndCancel(getFptr().fnQueryData());
    }

    @Override
    public void printLastReceipt() throws KktException {
        getFptr().setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_LAST_DOCUMENT);
        checkErrorCode(getFptr().report());

    }

    @Override
    public void income(float income) throws KktException {
        getFptr().setParam(IFptr.LIBFPTR_PARAM_SUM, income);
        checkErrorCode(getFptr().cashIncome());
    }

    @Override
    public void printXReport() throws KktException {
        getFptr().setParam(IFptr.LIBFPTR_PARAM_REPORT_TYPE, IFptr.LIBFPTR_RT_X);
        checkErrorCode(getFptr().report());
    }

    @Override
    public KktRepository setCurrentTime(ZonedDateTime zonedDateTime) throws KktException {
        getFptr().setParam(IFptr.LIBFPTR_PARAM_DATE_TIME, Date.from(zonedDateTime.toInstant()));
        return this;
    }

    @Override
    public State getCurrentShiftState() throws KktException {
        if (getFptr() == null) {
            return State.UNDEFINED;
        }

        getFptr().setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS);
        getFptr().queryData();

        long state = getFptr().getParamInt(IFptr.LIBFPTR_PARAM_SHIFT_STATE);

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
            throw new KktException(getFptr().errorDescription(), getFptr().errorCode());
        }
    }

    private void checkErrorAndCancel(int code) throws KktException {
        if (code != 0) {
            var exception = new KktException(getFptr().errorDescription(), getFptr().errorCode());
            checkErrorCode(getFptr().cancelReceipt());
            throw exception;
        }
    }

}
