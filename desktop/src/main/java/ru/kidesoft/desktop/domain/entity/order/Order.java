package ru.kidesoft.desktop.domain.entity.order;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Data
public class Order {
    private Integer id;
    private OperationType operationType;
    private PaymentType paymentType;
    private ZonedDateTime createdAt;
    private String cashier;
    private List<Ticket> tickets;
}
