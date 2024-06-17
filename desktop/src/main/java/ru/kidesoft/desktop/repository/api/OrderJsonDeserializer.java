package ru.kidesoft.desktop.repository.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import ru.kidesoft.desktop.domain.entity.order.Order;
import com.fasterxml.jackson.databind.JsonDeserializer;
import ru.kidesoft.desktop.domain.entity.order.PaymentType;
import ru.kidesoft.desktop.domain.entity.order.StatusType;
import ru.kidesoft.desktop.domain.entity.order.Ticket;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderJsonDeserializer extends JsonDeserializer<Order> {
    private static final ZoneId zone = ZoneId.of("Europe/Moscow");

    private static final DateTimeFormatter formatterForeign = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    private static final DateTimeFormatter longFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private static final DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public Order deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        JsonNode data = node.get("data");

        List<Ticket> ticketList = new ArrayList<>();

        ArrayNode tickets = (ArrayNode) data.get("tickets");
        for(JsonNode ticket : tickets) {
            var t = Ticket.builder()
                    .id(ticket.get("id").asInt())
                    .amount((float) ticket.get("amount").asDouble())
                    .number(ticket.get("number").asText())
                    .seatNumber(ticket.get("seat_number").asInt())
                    .rowSector(ticket.get("row_sector").asInt())
                    .zone(ticket.get("zona").asText())
                    .status(StatusType.getByName(ticket.get("status").asText()))
                    .ageLimit(ticket.get("event").get("show").get("age_limit").asText())
                    .showName(ticket.get("event").get("show").get("name").asText())
                    .dateTime(LocalDateTime.parse(ticket.get("event").get("date_time").asText(), formatterForeign).atZone(zone))

                    //.dateTime(ZonedDateTime.parse(ticket.get("event").get("date_time").asText(), formatterForeign))
                    .build();
            
            ticketList.add(t);
        }

        var orderBuilder = Order.builder();

        orderBuilder.id(data.get("id").asInt());

        if (data.has("kassir_name") && !data.get("kassir_name").asText().isEmpty()) {
            orderBuilder.cashier(data.get("kassir_name").asText());
        } else if(data.has("client_name") && !data.get("client_name").asText().isEmpty()) {
            orderBuilder.cashier(data.get("client_name").asText());
        } else if(data.has("user_name") && !data.get("user_name").asText().isEmpty()) {
            orderBuilder.cashier(data.get("user_name").asText());
        } else {
            orderBuilder.cashier("Нет данных");
        }

        if (data.has("payment_type") && !data.get("payment_type").asText().isEmpty()) {
            orderBuilder.paymentType(PaymentType.getByName(data.get("payment_type").asText()));
        }
        else if (data.has("order") && data.get("order").has("payment_type") && !data.get("order").get("payment_type").asText().isEmpty()) {
            orderBuilder.paymentType(PaymentType.getByName(data.get("order").get("payment_type").asText()));
        }
        else {
            orderBuilder.paymentType(PaymentType.UNDEFINED);
        }

        try {
            orderBuilder.createdAt(LocalDateTime.parse(data.get("date_time").asText(), shortFormatter).atZone(ZoneId.of("Europe/Moscow")));
        } catch (Exception e) {
            orderBuilder.createdAt(LocalDateTime.parse(data.get("date_time").asText(), formatterForeign).atZone(ZoneId.of("Europe/Moscow")));
        }

        orderBuilder.tickets(ticketList);

        return orderBuilder.build();
    }
}
