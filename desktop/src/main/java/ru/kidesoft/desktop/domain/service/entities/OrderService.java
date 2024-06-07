package ru.kidesoft.desktop.domain.service.entities;

import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.domain.dao.kkt.KktOperator;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.DbException;
import ru.kidesoft.desktop.repository.api.ApiRepositoryFactoryImpl;

@Service
public class OrderService {


    private final LoginService loginService;
    private final SettingService settingService;
    private final SessionService sessionService;
    private final ProfileService profileService;
    private final ApiRepositoryFactoryImpl apiRepositoryFactoryImpl;

    public OrderService(LoginService loginService, SettingService settingService, SessionService sessionService, ProfileService profileService, ApiRepositoryFactoryImpl apiRepositoryFactoryImpl) {
        this.loginService = loginService;
        this.settingService = settingService;
        this.sessionService = sessionService;
        this.profileService = profileService;
        this.apiRepositoryFactoryImpl = apiRepositoryFactoryImpl;
    }

    public Order getOrder(int orderId, SourceType sourceType, OperationType operationType) throws AppException {
        var login = loginService.getCurrentLogin();
        var setting = settingService.getByLogin(login);
        var session = sessionService.getByLogin(login);

        return apiRepositoryFactoryImpl
                .setHost(login.getUrl())
                .setTimeout(setting.getServerRequestTimeout())
                .setToken(session.getAccessToken())
                .setTokenType(session.getTokenType())
                .build().Order(orderId, sourceType).toBuilder().operationType(operationType).build();
    }
}
