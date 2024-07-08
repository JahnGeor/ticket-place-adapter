package ru.kidesoft.desktop.infrastructure.service.api;

import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.infrastructure.port.api.web.ApiRepositoryFactory;
import ru.kidesoft.desktop.infrastructure.port.api.dto.ClickDto;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.entity.session.Session;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.infrastructure.service.AuthService;
import ru.kidesoft.desktop.infrastructure.service.entities.LoginService;
import ru.kidesoft.desktop.infrastructure.service.entities.ProfileService;
import ru.kidesoft.desktop.infrastructure.service.entities.SessionService;
import ru.kidesoft.desktop.infrastructure.service.entities.SettingService;
import ru.kidesoft.desktop.repository.api.ApiRepositoryFactoryImpl;

import java.time.ZonedDateTime;

@Service
public class ApiService {
    private final SessionService sessionService;
    private final ApiRepositoryFactory apiRepositoryFactory;
    private final LoginService loginService;
    private final AuthService authService;
    private final ApiRepositoryFactoryImpl apiRepositoryFactoryImpl;
    private final ProfileService profileService;
    private final SettingService settingService;

    public ApiService(SessionService sessionService, ApiRepositoryFactory apiRepositoryFactory, LoginService loginService, AuthService authService, ApiRepositoryFactoryImpl apiRepositoryFactoryImpl, ProfileService profileService, SettingService settingService) {
        this.sessionService = sessionService;
        this.apiRepositoryFactory = apiRepositoryFactory;
        this.loginService = loginService;
        this.authService = authService;
        this.apiRepositoryFactoryImpl = apiRepositoryFactoryImpl;
        this.profileService = profileService;
        this.settingService = settingService;
    }

    public boolean isExpired(Session session) throws AppException {
        return session.getExpiredAt().isBefore(ZonedDateTime.now());
    }

    public ClickDto getClick() throws AppException {
        var session = sessionService.getCurrentSession();

        if (isExpired(session)) {
            authService.login(session.getLogin());
        }

        session = sessionService.getCurrentSession();
        var profile = profileService.getCurrentProfile();
        var setting = settingService.getCurrentSetting();

        return apiRepositoryFactoryImpl
                .setHost(session.getLogin().getUrl())
                .setTimeout(setting.getServerRequestTimeout())
                .setToken(session.getAccessToken())
                .setTokenType(session.getTokenType())
                .build().Click(profile.getUserId());
    }

    public Order getOrder(int orderId, SourceType sourceType) throws AppException {

        var session = sessionService.getCurrentSession();
        if (session == null) {
            return null;
        }

        if (isExpired(session)) {
            authService.login(session.getLogin());
        }

        session = sessionService.getCurrentSession();

        //var profile = profileService.getCurrentProfile();
        var setting = settingService.getCurrentSetting();

        return apiRepositoryFactoryImpl
                .setHost(session.getLogin().getUrl())
                .setTimeout(setting.getServerRequestTimeout())
                .setToken(session.getAccessToken())
                .setTokenType(session.getTokenType())
                .build().Order(orderId, sourceType);
    }
}
