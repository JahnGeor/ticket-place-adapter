package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

import ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums.PageOrientation;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums.PageSize;

public final class PrinterBuilder {
    private String name;
    private PageSize pageSize;
    private PageOrientation pageOrientation;
    private Boolean printCheck;
    private Boolean printTicket;

    private PrinterBuilder() {
    }

    public static PrinterBuilder aPrinter() {
        return new PrinterBuilder();
    }

    public PrinterBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public PrinterBuilder withPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PrinterBuilder withPageOrientation(PageOrientation pageOrientation) {
        this.pageOrientation = pageOrientation;
        return this;
    }

    public PrinterBuilder withPrintCheck(Boolean printCheck) {
        this.printCheck = printCheck;
        return this;
    }

    public PrinterBuilder withPrintTicket(Boolean printTicket) {
        this.printTicket = printTicket;
        return this;
    }

    public Printer build() {
        Printer printer = new Printer();
        printer.setName(name);
        printer.setPageSize(pageSize);
        printer.setPageOrientation(pageOrientation);
        printer.setPrintCheck(printCheck);
        printer.setPrintTicket(printTicket);
        return printer;
    }
}
