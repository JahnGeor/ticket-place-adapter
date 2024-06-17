package ru.kidesoft.desktop.domain.dao.printer;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.dao.printer.dto.PrinterDto;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.OrderDto;
import ru.kidesoft.desktop.domain.exception.AppException;

@Repository
public interface PrinterRepository {
    void print(OrderDto order, PrinterDto printer) throws AppException;
}
