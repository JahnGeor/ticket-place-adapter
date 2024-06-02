package ru.kidesoft.desktop.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kidesoft.desktop.domain.entity.profile.Profile;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
}