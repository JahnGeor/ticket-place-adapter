package ru.kidesoft.desktop.repository.database;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<Login, UUID> {
    @Modifying
    @Transactional
    @Override
    Login save(Login login);

    @Override
    Optional<Login> findById(UUID uuid);

    Optional<Login> findByEmailAndUrl(String email, String url);

    List<Login> findAll();

}
