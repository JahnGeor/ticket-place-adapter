package ru.kidesoft.desktop.infrastructure.port.api.dto;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.SourceType;

import java.io.IOException;
import java.time.ZonedDateTime;

@JsonDeserialize(using = Deserializer.class)
@Builder
@Data
public class ClickDto {
    private Integer id; // @Example 270843
    private Integer userId; // @Example 1
    private Integer orderId; // @Example 1026068
    private OperationType orderType; // @Example "order"
    private SourceType sourceType;
    private ZonedDateTime createdAt;  // @Example "2024-03-31T00:27:08.000000Z"

}

class Deserializer extends JsonDeserializer<ClickDto> {
    @Override
    public ClickDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        return ClickDto.builder().id(node.get("data").get("id").asInt())
                .createdAt(ZonedDateTime.parse(node.get("data").get("created_at").asText()))
                .orderType(OperationType.getByName(node.get("data").get("type").asText()))
                .sourceType(SourceType.getByName(node.get("data").get("type").asText()))
                .orderId(node.get("data").get("order_id").asInt())
                .userId(node.get("data").get("user_id").asInt()).build();
    }
}