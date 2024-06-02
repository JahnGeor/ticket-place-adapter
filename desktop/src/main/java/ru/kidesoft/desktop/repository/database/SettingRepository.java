package ru.kidesoft.desktop.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.setting.Setting;

import java.util.UUID;

@Repository
public interface SettingRepository extends JpaRepository<Setting, UUID> {

}
