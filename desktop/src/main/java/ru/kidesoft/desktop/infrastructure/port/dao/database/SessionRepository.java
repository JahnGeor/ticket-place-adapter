package ru.kidesoft.desktop.infrastructure.port.dao.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.session.Session;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    @Query("select s from Session s where s.login.id = ?1")
    Optional<Session> findByLoginId(UUID loginId);

    Optional<Session> findByLogin(Login login);

    Session save(Session session);
}
