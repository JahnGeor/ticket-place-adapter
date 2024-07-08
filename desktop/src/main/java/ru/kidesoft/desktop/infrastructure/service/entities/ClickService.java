package ru.kidesoft.desktop.infrastructure.service.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.infrastructure.port.api.web.ApiRepositoryFactory;
import ru.kidesoft.desktop.infrastructure.port.api.dto.ClickDto;
import ru.kidesoft.desktop.infrastructure.port.dao.database.ClickRepository;
import ru.kidesoft.desktop.domain.entity.click.Click;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.DbException;
import ru.kidesoft.desktop.domain.exception.UnspecifiedException;
import ru.kidesoft.desktop.repository.api.ApiRepositoryFactoryImpl;

@Service
public class ClickService {

    private final ApiRepositoryFactory apiRepositoryFactory;
    private final SettingService settingService;
    private final SessionService sessionService;
    private final ProfileService profileService;
    private final ApiRepositoryFactoryImpl apiRepositoryFactoryImpl;
    private Logger logger = LogManager.getLogger(ClickService.class);

    private final LoginService loginService;
    private final ClickRepository clickRepository;

    public ClickService(LoginService loginService, ClickRepository clickRepository, ApiRepositoryFactory apiRepositoryFactory, SettingService settingService, SessionService sessionService, ProfileService profileService, ApiRepositoryFactoryImpl apiRepositoryFactoryImpl) {
        this.loginService = loginService;
        this.clickRepository = clickRepository;
        this.apiRepositoryFactory = apiRepositoryFactory;
        this.settingService = settingService;
        this.sessionService = sessionService;
        this.profileService = profileService;
        this.apiRepositoryFactoryImpl = apiRepositoryFactoryImpl;
    }

    public Click getByLogin(Login login) throws AppException {
        var click = clickRepository.findByLogin(login).orElseThrow(
                () -> {
                    logger.error("Не удалось получить последний клик пользователя {}", login.getId());
                    return new DbException("Не удалось получить последний клик пользователя");
                }
        );

        logger.info("Получен последний клик пользователя {}", login.getId());
        return click;
    }

    public Click getCurrentClick() throws AppException {
        var login = loginService.getCurrentLogin();
        return getByLogin(login);
    }

    public ClickDto getClickFromRemote() throws AppException {
        var login = loginService.getCurrentLogin();
        var setting = settingService.getByLogin(login);
        var session = sessionService.getByLogin(login);
        var profile = profileService.getByLogin(login);

        var click = apiRepositoryFactory.setHost(login.getUrl()).setTimeout(setting.getServerRequestTimeout()).setToken(session.getAccessToken()).setTokenType(session.getTokenType()).build().Click(profile.getUserId());

        logger.info("Получен клик {}", click.getId());
        return click;
    }

    public Click save(Click click) throws AppException {

        var login = loginService.getCurrentLogin();

        clickRepository.findByLogin(login).ifPresentOrElse(
                c -> {
                    click.setId(c.getId());
                    logger.info("Найден клик с внутренним id: {}", c.getId());
                }
        , () -> {
                    logger.info("Клик для пользователя {} не найден в базе данных", login.getId());
                });

        click.setLogin(login);

        var resultClick = clickRepository.save(click);

        logger.info("Сохранен клик с внутренним id: {}", resultClick.getId());

        return resultClick;
    }

    public void clickInitialize() throws AppException {
        var clickDto = getClickFromRemote();

        try {
            var login = loginService.getCurrentLogin();

            Click clickFromRemote = Click.builder().clickId(clickDto.getId()).login(login).build();

            save(clickFromRemote);


        }  catch (Exception e) {
            logger.error("Не удалось сохранить клик {}", clickDto.getId());

            if (e instanceof AppException) {
                throw e;
            }

            throw new UnspecifiedException(e);
        }


    }

    /*
    *    apiRepositoryFactoryImpl
    *            .setHost(login.getUrl())
    *            .setTimeout(setting.getServerRequestTimeout())
    *            .setToken(session.getAccessToken()).build()
    *            .Click(profile.getUserId());
    *  */
}
