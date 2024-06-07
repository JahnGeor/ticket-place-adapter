package ru.kidesoft.desktop.domain.entity.order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.domain.exception.BusinessRulesException;
import ru.kidesoft.desktop.repository.api.OrderJsonDeserializer;

import javax.xml.transform.Source;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@Data
@JsonDeserialize(using = OrderJsonDeserializer.class)
public class Order {
    private Integer id;
    private SourceType sourceType;
    private PaymentType paymentType;
    private ZonedDateTime createdAt;
    private String cashier;
    private List<Ticket> tickets;
    private OperationType operationType;
    // TODO: Обработчики


    public static class OrderBuilder {
        public Order buildFor(PrintType printType) throws BusinessRulesException {

            return switch (printType) {
                case UNDEFINED -> throw new BusinessRulesException("Неизвестный тип печати");
                case CHECK -> this.removeZeroAmountPositions().removeByPaymentType().removeByPrintType(printType).build();
                case TICKET -> this.removeByPrintType(printType).build();
            };

            //TODO: Если тип данных - чек, то:
            //TODO: удаляем все нулевые позиции +
            //TODO: удаляем все чеки с необрабатываемой формой оплаты
            // Удаляем все чеки, которые не подходят по статусу -> в билетах
        }

        public OrderBuilder removeZeroAmountPositions() throws BusinessRulesException {
            this.tickets.removeIf(ticket -> ticket.getAmount() == 0);

            if (this.tickets.isEmpty()) {
                throw new BusinessRulesException("Нет ненулевых позиций");
            }

            return this;
        }

        public OrderBuilder removeByPaymentType() throws BusinessRulesException {
            return switch (this.paymentType) {
                case CASH, CARD, ACCOUNT_INDIVIDUAL -> this;
                default -> throw new BusinessRulesException(String.format("Тип оплаты \"%s\" не обрабатывается", this.paymentType.getDescription()));
            };
        }

        public OrderBuilder removeByPrintType(PrintType printType) throws BusinessRulesException{
            var statusDescription = this.tickets.stream().map(ticket -> ticket.getStatus().getDescription()).distinct().collect(Collectors.joining(", "));

            switch (printType) {
                case CHECK -> this.tickets.removeIf(ticket -> ticket.getStatus() != StatusType.PAYED && ticket.getStatus() != StatusType.RETURNED);
                case TICKET -> this.tickets.removeIf(ticket -> ticket.getStatus() != StatusType.PAYED && ticket.getStatus() != StatusType.CREATED);
                default -> {
                    throw new BusinessRulesException("Неизвестный тип печати");
                }
            }

            if (this.tickets.isEmpty()) {
                throw new BusinessRulesException(String.format("После обработки по статусу не осталось позиций. Набор позиций до удаления: \"%s\"", statusDescription));
            }

            return this;
        }
    }

}

// Для чеков: убрать все нулевые позиции из Tickets
