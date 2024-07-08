package ru.kidesoft.desktop.infrastructure.port.dao.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.history.History;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {
    Optional<History> findHistoryByLoginAndOrderId(Login login, Integer orderId);
    List<History> findAllByLogin(Login login);

}
