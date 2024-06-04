package ru.kidesoft.desktop.domain.dao.database;

import lombok.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.login.Login;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<Login, UUID> {
    @Override
    Optional<Login> findById(UUID uuid);

    Optional<Login> findByEmailAndUrl(String email, String url);

    List<Login> findAll();



    @Query("SELECT l from Constant c left join Login l on cast(c.val as uuid) = l.id WHERE c.name = 'ACTIVE_USER_ID'")
    Optional<Login> findCurrentLogin();


}
