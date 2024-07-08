package ru.kidesoft.desktop.infrastructure.port.dao.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.constant.Constant;
import ru.kidesoft.desktop.domain.entity.constant.ConstantEnums;

import java.util.Optional;

@Repository
public interface ConstantRepository extends JpaRepository<Constant, String> {
    Constant save(Constant constant);

    @Override
    void delete(Constant entity);

    Optional<Constant> getConstantByName(ConstantEnums name);

    Optional<Constant> findByName(ConstantEnums name);
}
