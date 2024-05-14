package ru.kidesoft.ticketplace.client.domain.models.entities.history;

import ru.kidesoft.ticketplace.client.domain.models.entities.history.enums.StatusType;
import ru.kidesoft.ticketplace.client.domain.models.entities.order.enums.OperationType;

import java.time.ZonedDateTime;
import java.util.UUID;

public class History {
    private UUID ID;
    private ZonedDateTime createdAt;
    private StatusType status;
    private String error;
    private Integer orderId;
    private OperationType operationType;

    public History() {
    }

    public History(ZonedDateTime createdAt, StatusType status, String error, Integer orderId, OperationType operationType) {
        this.createdAt = createdAt;
        this.status = status;
        this.error = error;
        this.orderId = orderId;
        this.operationType = operationType;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
