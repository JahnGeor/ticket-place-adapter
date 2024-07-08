package ru.kidesoft.desktop.infrastructure.port.api.kkt;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.exception.KktException;

import java.time.ZonedDateTime;

@Repository
public interface KktSystem {
    boolean isConnectionOpened() throws KktException;
    KktSystem setOperator(KktOperator kktOperator) throws KktException;
    KktSystem setCurrentTime(ZonedDateTime zonedDateTime) throws KktException;
    State getCurrentShiftState() throws KktException;
    KktPrinter getPrinter() throws KktException;
}
