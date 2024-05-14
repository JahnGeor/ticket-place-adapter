package ru.kidesoft.ticketplace.client.domain.dao.api.dto.authorization;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.ProfileBuilder;
import ru.kidesoft.ticketplace.client.domain.models.entities.profile.enums.RoleType;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.Session;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.SessionBuilder;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.TokenData;
import ru.kidesoft.ticketplace.client.domain.models.entities.session.TokenDataBuilder;

import java.io.IOException;
import java.time.ZonedDateTime;

public class Deserializer extends StdDeserializer<AuthorizationDto> {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectMapper getObjectMapper() {
        SimpleModule mode = new SimpleModule().addDeserializer(AuthorizationDto.class, new Deserializer());
        return objectMapper.registerModule(mode);
    }

    public Deserializer() {
        this(null);
    }

    protected Deserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public AuthorizationDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws JacksonException, IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        var tokenData =
                TokenDataBuilder.aTokenData().
                        withAccessToken(node.get("accessToken").asText()).
                        withTokenType(node.get("token_type").asText()).
                        build();


        var session = SessionBuilder.aSession()
                .withTokenData(tokenData)
                .withCreatedAt(ZonedDateTime.now())
                .withExpiredAt(ZonedDateTime.now().plusDays(1))
                .build();


        JsonNode userData = node.get("userData");

        var profile = ProfileBuilder.aProfile().
                withAvatar(userData.get("avatar").asText()).
                withInn(userData.get("inn").asLong()).
                withFullName(userData.get("fullName").asText()).
                withUserName(userData.get("username").asText()).
                withUserId(userData.get("id").asInt()).
                withRole(RoleType.getByName(userData.get("role").asText())).
                build();


        return AuthorizationDtoBuilder.anAuthorizationDto()
                .withProfile(profile)
                .withSession(session)
                .build();
    }

}
