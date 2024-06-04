package ru.kidesoft.desktop.repository.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import ru.kidesoft.desktop.domain.entity.order.Order;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.kidesoft.desktop.domain.entity.order.Ticket;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderJsonDeserializer extends JsonDeserializer<Order> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public Order deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        JsonNode data = node.get("data");

        List<Ticket> ticketList = new ArrayList<>();

        ArrayNode tickets = (ArrayNode) data.get("tickets");
        for(JsonNode ticket : tickets) {
            Ticket
            t.setId(ticket.get("id").asInt());
            t.setAmount((float) ticket.get("amount").asDouble());
            t.setNumber(ticket.get("number").asText());
            t.setZone(ticket.get("zona").asText());
            t.setStatus(StatusType.getByName(ticket.get("status").asText()));

            JsonNode event = ticket.get("event");

            t.setAgeLimit(event.get("show").get("age_limit").asText());
            t.setShowName(event.get("show").get("name").asText());
            t.setDateTime(ZonedDateTime.parse(event.get("date_time").asText(), formatter));

            ticketList.add(t);
        }

        Order order = new Order();

        order.setId(data.get("id").asInt());

        if (data.has("kassir_name") && !data.get("kassir_name").isEmpty()) {
            order.setCashier(data.get("kassir_name").asText());
        } else if(data.has("client_name") && !data.get("client_name").isEmpty()) {
            order.setCashier(data.get("client_name").asText());
        } else if(data.has("user_name") && !data.get("user_name").isEmpty()) {
            order.setCashier(data.get("user_name").asText());
        } else {
            order.setCashier("Нет данных");
        }

        if (data.has("payment_type") && !data.get("payment_type").isEmpty()) {
            order.setPaymentType(PaymentType.getByName(data.get("payment_type").asText()));
        }
        else if (data.has("order") && data.get("order").has("payment_type") && !data.get("order").get("payment_type").isEmpty()) {
            order.setPaymentType(PaymentType.getByName(data.get("order").get("payment_type").asText()));
        }
        else {
            order.setPaymentType(PaymentType.UNDEFINED);
        }

        order.setCreatedAt(LocalDateTime.parse(data.get("date_time").asText(), shortFormatter).atZone(ZoneId.of("Europe/Moscow")));

        order.setTickets(ticketList);

        return order;
    }
}
