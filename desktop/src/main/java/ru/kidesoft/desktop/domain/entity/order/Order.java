package ru.kidesoft.desktop.domain.entity.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.repository.api.OrderJsonDeserializer;

import javax.xml.transform.Source;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@Data
@JsonDeserialize(using = OrderJsonDeserializer.class)
public class Order {
    private Integer id;
    private SourceType sourceType;
    private PaymentType paymentType;
    private ZonedDateTime createdAt;
    private String cashier;
    private List<Ticket> tickets;
}
