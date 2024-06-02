package ru.kidesoft.desktop.domain.dao;

import lombok.Data;
import ru.kidesoft.desktop.domain.entity.profile.Profile;
import ru.kidesoft.desktop.domain.entity.session.Session;

@Data
public class LoginDto {
    private Session session;
    private Profile profile;
}
