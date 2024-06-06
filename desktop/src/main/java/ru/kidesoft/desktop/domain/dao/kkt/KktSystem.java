package ru.kidesoft.desktop.domain.dao.kkt;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.exception.KktException;

import java.time.ZonedDateTime;

@Repository
public interface KktSystem {
    void cancelReceipt();
    boolean isConnectionOpened() throws KktException;
    KktRepository openConnection() throws KktException;
    KktRepository closeConnection() throws KktException;
    KktRepository setOperator(KktOperator kktOperator) throws KktException;
    KktRepository setCurrentTime(ZonedDateTime zonedDateTime) throws KktException;
    State getCurrentShiftState() throws KktException;
}
