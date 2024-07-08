package ru.kidesoft.desktop.repository.kkt.atol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.atol.drivers10.fptr.IFptr;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktOperator;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktPrinter;
import ru.kidesoft.desktop.infrastructure.port.api.kkt.KktSystem;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.exception.KktException;

import java.time.ZonedDateTime;
import java.util.Date;

@Repository
public class AtolSystemImpl implements KktSystem {

    AtolRepositoryImpl kktRepository;
    AtolPrinterImpl printer;

    @Autowired
    public AtolSystemImpl(AtolRepositoryImpl kktRepository, AtolPrinterImpl printer) {
        this.kktRepository = kktRepository;
        this.printer = printer;
    }



    @Override
    public boolean isConnectionOpened() throws KktException {
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS);

        if (kktRepository.getFptr().queryData() != IFptr.LIBFPTR_OK) {
            return false;
        }

        return kktRepository.getFptr().isOpened();
    }



    @Override
    public KktSystem setOperator(KktOperator kktOperator) throws KktException {
        kktRepository.getFptr().setParam(1021, kktOperator.getFullName());
        kktRepository.getFptr().setParam(1203, kktOperator.getInn().toString());

        kktRepository.checkErrorCode(kktRepository.getFptr().operatorLogin());
        return this;
    }

    @Override
    public KktSystem setCurrentTime(ZonedDateTime zonedDateTime) throws KktException {
        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_DATE_TIME, Date.from(zonedDateTime.toInstant()));
        kktRepository.checkErrorCode(kktRepository.getFptr().writeDateTime());


        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_DATE_TIME);
        kktRepository.getFptr().queryData();

        Date dateTime =  kktRepository.getFptr().getParamDateTime(IFptr.LIBFPTR_PARAM_DATE_TIME);

        return this;
    }

    @Override
    public State getCurrentShiftState() throws KktException {
        if (kktRepository.getFptr() == null) {
            return State.UNDEFINED;
        }

        kktRepository.getFptr().setParam(IFptr.LIBFPTR_PARAM_DATA_TYPE, IFptr.LIBFPTR_DT_STATUS);
        kktRepository.getFptr().queryData();

        long state = kktRepository.getFptr().getParamInt(IFptr.LIBFPTR_PARAM_SHIFT_STATE);

        return switch ((int) state) {
            case IFptr.LIBFPTR_SS_CLOSED -> State.CLOSED;
            case IFptr.LIBFPTR_SS_OPENED -> State.OPENED;
            case IFptr.LIBFPTR_SS_EXPIRED -> State.EXPIRED;
            default -> State.UNDEFINED;
        };
    }


    @Override
    public KktPrinter getPrinter() throws KktException {
        return this.printer;
    }


}
