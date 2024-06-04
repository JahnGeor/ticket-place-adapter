package ru.kidesoft.desktop.domain.service;

import javafx.print.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.SourceType;

@Service
public class PrinterService {
    ConfigurableApplicationContext applicationContext;

    @Autowired
    public PrinterService(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void print(Order order, OperationType operationType) {

    }

    public void print(int orderId, SourceType sourceType, OperationType operationType) {
        // TODO: getOrder

        // print(order, operationType);
    }
}
