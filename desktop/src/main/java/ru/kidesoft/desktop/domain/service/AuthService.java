package ru.kidesoft.desktop.domain.service;

import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;


import ru.kidesoft.desktop.domain.dao.api.ApiRepositoryFactory;
import ru.kidesoft.desktop.domain.dao.database.*;
import ru.kidesoft.desktop.domain.dao.kkt.KktRepository;
import ru.kidesoft.desktop.domain.entity.constant.Constant;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.constant.ConstantEnums;
import ru.kidesoft.desktop.domain.entity.setting.Setting;
import ru.kidesoft.desktop.domain.exception.AppException;

import java.util.Optional;

@Service
public class AuthService {

    private final Logger logger = LogManager.getLogger(AuthService.class);
    private final LoginRepository loginRepository;
    private final ConstantRepository constantRepository;
    private final ProfileRepository profileRepository;
    private final SessionRepository sessionRepository;
    private final SettingRepository settingRepository;
    private final ApiRepositoryFactory apiRepositoryFactory;
    private final KktRepository kktRepository;
    private ConfigurableApplicationContext applicationContext;
    @Autowired
    public AuthService(
            LoginRepository loginRepository,
            ConstantRepository constantRepository,
            ProfileRepository profileRepository,
            SessionRepository sessionRepository,
            SettingRepository settingRepository,
            ApiRepositoryFactory apiRepositoryFactory,
            KktRepository kktRepository,
            ConfigurableApplicationContext applicationContext) {
        this.loginRepository = loginRepository;
        this.constantRepository = constantRepository;
        this.profileRepository = profileRepository;
        this.sessionRepository = sessionRepository;
        this.settingRepository = settingRepository;
        this.apiRepositoryFactory = apiRepositoryFactory;
        this.kktRepository = kktRepository;
        this.applicationContext = applicationContext;
    }

    public Optional<Login> getActiveLogin() {
        var login = loginRepository.findCurrentLogin();
        logger.trace("Активный пользователь = {}", login);
        return login;
    }

    @Transactional
    public void login(Login login) throws AppException {
        var profileSessionDto = apiRepositoryFactory.setHost(login.getUrl()).build().Login(login.getEmail(), login.getPassword());

        loginRepository.findByEmailAndUrl(login.getEmail(), login.getUrl()).ifPresent(loginExists -> {
            login.setId(loginExists.getId());
        });

        var resultLogin = loginRepository.save(login);

        profileRepository.findByLogin(resultLogin).ifPresent(profile -> {
            logger.trace("Найден профиль пользователя {}, ID профиля = {}", login.getEmail(), profile.getId());
            profileSessionDto.getProfile().setId(profile.getId());
        });

        profileSessionDto.getProfile().setLogin(login);

        var resultProfile = profileRepository.save(profileSessionDto.getProfile());

        logger.trace("Сохранение профиля пользователя {} завершено успешно. Profile ID = {}", login.getEmail(), resultProfile.getId());

        sessionRepository.findByLogin(resultLogin).ifPresent(session -> {
            logger.trace("Найден сессия пользователя {}, ID сессии = {}", login.getEmail(), session.getId());
            profileSessionDto.getSession().setId(session.getId());
        });

        profileSessionDto.getSession().setLogin(resultLogin);

        sessionRepository.save(profileSessionDto.getSession());

        logger.trace("Сохранение сессии пользователя {} завершено успешно. Session ID = {}", login.getEmail(), profileSessionDto.getSession().getId());

        Setting setting = Setting.builder().login(resultLogin).build();

        Optional<Setting> settingOpt = settingRepository.findByLogin(resultLogin);

        if (settingOpt.isEmpty()) {
            setting = settingRepository.save(setting);
        } else {
            setting = settingOpt.get();
        }


        constantRepository.save(Constant.builder().val(resultLogin.getId().toString()).name(ConstantEnums.ACTIVE_USER_ID).build());

        logger.trace("Сохранение константы {} = {} завершено успешно", ConstantEnums.ACTIVE_USER_ID, resultLogin.getId());

        logger.info("Авторизация пользователя {} на сервисе {} завершена успешно", login.getEmail(), login.getUrl());
    }

    public void logout() {
        constantRepository.delete(Constant.builder().name(ConstantEnums.ACTIVE_USER_ID).build());
    }

}
