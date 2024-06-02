package ru.kidesoft.desktop.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.history.History;

import java.util.UUID;
@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {

}
