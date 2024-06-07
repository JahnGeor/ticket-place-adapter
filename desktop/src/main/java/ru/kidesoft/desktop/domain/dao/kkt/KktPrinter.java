package ru.kidesoft.desktop.domain.dao.kkt;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.exception.KktException;

@Repository
public interface KktPrinter {
    void cancelReceipt() throws KktException;
    void print(Order order) throws KktException;
    void printLastReceipt() throws KktException;
    void income(float income) throws KktException;
    void printXReport() throws KktException;
    void openShift() throws KktException;
    void closeShift() throws KktException;
}
