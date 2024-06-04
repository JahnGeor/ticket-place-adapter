package ru.kidesoft.desktop.domain.dao.api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.kidesoft.desktop.domain.entity.profile.Profile;
import ru.kidesoft.desktop.domain.entity.session.Session;
import ru.kidesoft.desktop.repository.api.ProfileSessionDtoDeserializer;

@Builder
@Data
@JsonDeserialize(using = ProfileSessionDtoDeserializer.class)
public class ProfileSessionDto {
    private Session session;
    private Profile profile;
}
