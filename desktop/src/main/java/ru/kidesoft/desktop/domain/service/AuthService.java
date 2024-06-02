package ru.kidesoft.desktop.domain.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.controller.javafx.dto.auth.AuthUiDto;
import ru.kidesoft.desktop.controller.javafx.dto.auth.CashierUiDto;
import ru.kidesoft.desktop.domain.dao.ApiRepository;
import ru.kidesoft.desktop.domain.entity.constant.Constant;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.constant.ConstantEnums;
import ru.kidesoft.desktop.domain.entity.profile.Profile;
import ru.kidesoft.desktop.domain.exception.ApiException;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.repository.api.ApiRepositoryImpl;
import ru.kidesoft.desktop.repository.database.ProfileRepository;
import ru.kidesoft.desktop.repository.database.ConstantRepository;
import ru.kidesoft.desktop.repository.database.LoginRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final Logger logger = LogManager.getLogger(AuthService.class);

    private final ApiRepository apiRepository;

    private final LoginRepository loginRepository;
    private final ConstantRepository constantRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public AuthService(ApiRepository apiRepository, LoginRepository loginRepository, ConstantRepository constantRepository,
                       ProfileRepository profileRepository) {
        this.apiRepository = apiRepository;
        this.loginRepository = loginRepository;
        this.constantRepository = constantRepository;
        this.profileRepository = profileRepository;
    }

    public Optional<UUID> isActive() {
        Optional<Constant> constant = constantRepository.getConstantByName(ConstantEnums.ACTIVE_USER_ID);
        return constant.map(value -> UUID.fromString(value.getVal()));
    }

    public UUID login (Login login) throws AppException {
        // var loginDto = apiRepository.Login(login);
        var loginId = loginRepository.save(login).getId();

        // TODO: Сохранить профиль
        // TODO: Сохранить сеанс

        constantRepository.save(Constant.builder().val(loginId.toString()).name(ConstantEnums.ACTIVE_USER_ID).build());
        return loginId;
    }

    public void logout() {
        constantRepository.delete(Constant.builder().name(ConstantEnums.ACTIVE_USER_ID).build());
    }

    public AuthUiDto getAuthDto() {
        logger.info("Запрос данных для окна авторизации");

        List<Login> loginList = loginRepository.findAll();
        logger.trace("Запрос данных по пользователям завершен, количество записей: {}", loginList.size());

        List<Profile> profileList = profileRepository.findAll();
        logger.trace("Запрос данных по кассирам завершен, количество записей: {}", profileList.size());

        List<CashierUiDto> cashierList = profileList.stream().map(profile ->
                CashierUiDto.builder()
                        .fullName(profile.getFullname())
                        .inn(profile.getInn())
                        .build()
        ).distinct().toList();


        return AuthUiDto.builder()
                .login(loginList)
                .cashier(cashierList)
                .build();
    }

}
