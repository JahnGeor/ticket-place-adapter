package ru.kidesoft.ticketplace.client.domain.models.entities.order;

import ru.kidesoft.ticketplace.client.domain.models.entities.order.enums.OperationType;
import ru.kidesoft.ticketplace.client.domain.models.entities.order.enums.PaymentType;

import java.time.ZonedDateTime;
import java.util.List;

public class Order {
    private Integer id;
    private OperationType operationType;
    private PaymentType paymentType;
    private ZonedDateTime createdAt;
    private String cashier;
    private List<Ticket> tickets;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCashier() {
        return cashier;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    boolean isValidPaymentType() {
        return true;
    }
    boolean isNonZeroAmount() {
        return true;
    }
    boolean getDataByPrintCase() {
        return true;
    }
}
