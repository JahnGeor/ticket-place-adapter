package ru.kidesoft.desktop.repository.printer;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.dao.printer.PrinterRepository;
import ru.kidesoft.desktop.domain.entity.order.Order;

@Repository
public class PrinterRepositoryImpl implements PrinterRepository {
    @Override
    public void print(Order order) {

    }
}
