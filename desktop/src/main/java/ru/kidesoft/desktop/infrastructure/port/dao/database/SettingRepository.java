package ru.kidesoft.desktop.infrastructure.port.dao.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.history.History;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.setting.Setting;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingRepository extends JpaRepository<Setting, UUID> {
    @Override
    Setting save(Setting s);

    @Query("select s from Setting s where s.login = ?1")
    Optional<Setting> findByLogin(Login login);


}
