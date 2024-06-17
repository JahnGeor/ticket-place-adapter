package ru.kidesoft.desktop.domain.dao.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.profile.Cashier;
import ru.kidesoft.desktop.domain.entity.profile.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    @Query("select p from Profile p where p.login.id = ?1")
    Optional<Profile> findByLoginId(UUID loginId);

    @Query("select new ru.kidesoft.desktop.domain.entity.profile.Cashier(p.fullname, p.inn) from Profile p")
    List<Cashier> findCashierList();

    Optional<Profile> findByLogin(Login uuid);

    Profile save(Profile profile);
}