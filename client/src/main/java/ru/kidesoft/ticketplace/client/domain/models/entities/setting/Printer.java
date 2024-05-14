package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

import ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums.PageOrientation;
import ru.kidesoft.ticketplace.client.domain.models.entities.setting.enums.PageSize;

public class Printer {
    private String name;
    private PageSize pageSize;
    private PageOrientation pageOrientation;
    private Boolean printCheck;
    private Boolean printTicket;

    public Printer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageSize getPageSize() {
        return pageSize;
    }

    public void setPageSize(PageSize pageSize) {
        this.pageSize = pageSize;
    }

    public PageOrientation getPageOrientation() {
        return pageOrientation;
    }

    public void setPageOrientation(PageOrientation pageOrientation) {
        this.pageOrientation = pageOrientation;
    }

    public Boolean getPrintCheck() {
        return printCheck;
    }

    public void setPrintCheck(Boolean printCheck) {
        this.printCheck = printCheck;
    }

    public Boolean getPrintTicket() {
        return printTicket;
    }

    public void setPrintTicket(Boolean printTicket) {
        this.printTicket = printTicket;
    }
}
