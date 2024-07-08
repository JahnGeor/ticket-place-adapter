package ru.kidesoft.desktop.infrastructure.port.api.printer;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.infrastructure.port.api.printer.dto.PrinterDto;
import ru.kidesoft.desktop.domain.entity.order.OrderDto;
import ru.kidesoft.desktop.domain.exception.AppException;

@Repository
public interface PrinterRepository {
    void print(OrderDto order, PrinterDto printer) throws AppException;
}
