package ru.kidesoft.desktop.infrastructure.port.dao.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kidesoft.desktop.domain.entity.click.Click;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.util.Optional;
import java.util.UUID;

public interface ClickRepository extends JpaRepository<Click, UUID> {
    @Query("select c from Click c where c.login = ?1")
    Optional<Click> findByLogin(Login login);
}