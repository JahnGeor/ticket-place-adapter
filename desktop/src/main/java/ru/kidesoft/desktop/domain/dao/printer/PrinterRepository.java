package ru.kidesoft.desktop.domain.dao.printer;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.order.Order;

@Repository
public interface PrinterRepository {
    void print(Order order);
}
