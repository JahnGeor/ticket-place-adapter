package ru.kidesoft.desktop.domain.dao.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.history.History;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.util.UUID;
@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {


}