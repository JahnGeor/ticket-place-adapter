package ru.kidesoft.ticketplace.client.domain.models.entities.order;

import ru.kidesoft.ticketplace.client.domain.models.entities.order.enums.PrintType;
import ru.kidesoft.ticketplace.client.domain.models.entities.order.enums.StatusType;

import java.time.ZonedDateTime;

public class Ticket {
    private Integer id;
    private String number;
    private StatusType status;
    private String zone;
    private Integer seatNumber;
    private Integer rowSector;
    private Float amount;
    private String showName;
    private String ageLimit;
    private ZonedDateTime dateTime;

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }

    boolean isValidStatus(PrintType print) {
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getRowSector() {
        return rowSector;
    }

    public void setRowSector(Integer rowSector) {
        this.rowSector = rowSector;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}