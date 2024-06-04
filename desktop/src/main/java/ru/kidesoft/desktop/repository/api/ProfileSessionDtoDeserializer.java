package ru.kidesoft.desktop.repository.api;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.kidesoft.desktop.domain.dao.api.dto.ProfileSessionDto;
import ru.kidesoft.desktop.domain.entity.profile.Profile;
import ru.kidesoft.desktop.domain.entity.profile.RoleType;
import ru.kidesoft.desktop.domain.entity.session.Session;

import java.io.IOException;
import java.time.ZonedDateTime;

public class ProfileSessionDtoDeserializer extends JsonDeserializer<ProfileSessionDto> {

    @Override
    public ProfileSessionDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Profile profile = new Profile();
        Session session = new Session();

        session.setTokenType(node.get("token_type").asText());
        session.setAccessToken(node.get("accessToken").asText());

        session.setCreatedAt(ZonedDateTime.now());
        session.setExpiredAt(ZonedDateTime.now().plusDays(1));
        // TODO: убрать хард-код из таймаута истечения действия

        JsonNode userData = node.get("userData");

        profile.setAvatar(userData.get("avatar").asText());
        profile.setInn(userData.get("inn").asLong());
        profile.setFullname(userData.get("fullName").asText());
        profile.setUsername(userData.get("username").asText());
        profile.setUserId(userData.get("id").asInt());
        profile.setRole(RoleType.getByName(userData.get("role").asText()));

        return ProfileSessionDto.builder().session(session).profile(profile).build();
    }
}
