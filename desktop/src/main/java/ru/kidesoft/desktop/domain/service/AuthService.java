package ru.kidesoft.desktop.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.entity.constant.Constant;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.constant.ConstantEnums;
import ru.kidesoft.desktop.repository.database.ConstantRepository;
import ru.kidesoft.desktop.repository.database.LoginRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    LoginRepository loginRepository;
    ConstantRepository constantRepository;
    @Autowired
    public AuthService(LoginRepository loginRepository, ConstantRepository constantRepository) {
        this.loginRepository = loginRepository;
        this.constantRepository = constantRepository;
    }

    public Optional<UUID> isActive() {
        Optional<Constant> constant = constantRepository.getConstantByName(ConstantEnums.ACTIVE_USER_ID);
        return constant.map(value -> UUID.fromString(value.getVal()));
    }

    public UUID login (Login login) {
        Optional<Login> loginOptional = loginRepository.findByEmailAndUrl(login.getEmail(), login.getUrl());

        UUID id;

        if (loginOptional.isEmpty()) {
            id = loginRepository.save(login).getId();
        } else {
            id = loginOptional.get().getId();
        }

        constantRepository.save(Constant.builder().val(id.toString()).name(ConstantEnums.ACTIVE_USER_ID).build());
        return id;
    }

    public void logout() {
        constantRepository.delete(Constant.builder().name(ConstantEnums.ACTIVE_USER_ID).build());
    }

    public List<Login> findAll() {
        return loginRepository.findAll();
    }

}
