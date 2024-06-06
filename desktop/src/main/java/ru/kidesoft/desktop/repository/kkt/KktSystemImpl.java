package ru.kidesoft.desktop.repository.kkt;

import lombok.Getter;
import lombok.Setter;
import ru.atol.drivers10.fptr.IFptr;
import ru.kidesoft.desktop.domain.dao.kkt.KktOperator;
import ru.kidesoft.desktop.domain.dao.kkt.KktRepository;
import ru.kidesoft.desktop.domain.dao.kkt.KktSystem;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.exception.KktException;

import java.time.ZonedDateTime;

public class KktSystemImpl implements KktSystem {

    @Setter private IFptr fptr;

    public IFptr getFptr() throws KktException {
        if (fptr == null) {
            throw new KktException("KKT driver not initialized");
        }
        return fptr;
    }

    @Override
    public void cancelReceipt() {

    }

    @Override
    public boolean isConnectionOpened() throws KktException {
        return false;
    }

    @Override
    public KktRepository openConnection() throws KktException {
        return null;
    }

    @Override
    public KktRepository closeConnection() throws KktException {
        return null;
    }

    @Override
    public KktRepository setOperator(KktOperator kktOperator) throws KktException {
        return null;
    }

    @Override
    public KktRepository setCurrentTime(ZonedDateTime zonedDateTime) throws KktException {
        return null;
    }

    @Override
    public State getCurrentShiftState() throws KktException {
        return null;
    }
}
