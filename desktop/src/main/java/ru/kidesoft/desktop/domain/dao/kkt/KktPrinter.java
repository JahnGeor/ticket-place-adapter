package ru.kidesoft.desktop.domain.dao.kkt;

import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.exception.KktException;

public interface KktPrinter {
    void print(Order order, OperationType operationType) throws KktException;
    void printLastReceipt() throws KktException;
    void income(float income) throws KktException;
    void printXReport() throws KktException;
    void openShift() throws KktException;
    void closeShift() throws KktException;
}
