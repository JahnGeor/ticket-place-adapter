package ru.kidesoft.desktop.domain.dao.kkt;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.exception.KktException;

import java.time.ZonedDateTime;

@Repository
public interface KktRepository {
    boolean isConnectionOpened() throws KktException ;
    KktRepository setConnection(KktSetting kktSetting) throws KktException;
    KktRepository setOperator(KktOperator kktOperator) throws KktException;
    void openShift() throws KktException;
    void closeShift() throws KktException;
    KktRepository openConnection() throws KktException;
    KktRepository closeConnection() throws KktException;
    void print(Order order, OperationType operationType) throws KktException;
    void printLastReceipt() throws KktException;
    void income(float income) throws KktException;
    void printXReport() throws KktException;
    KktRepository setCurrentTime(ZonedDateTime zonedDateTime) throws KktException;
    State getCurrentShiftState() throws KktException;
}
