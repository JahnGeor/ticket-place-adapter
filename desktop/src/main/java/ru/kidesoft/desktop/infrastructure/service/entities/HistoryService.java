package ru.kidesoft.desktop.infrastructure.service.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.kidesoft.desktop.infrastructure.port.dao.database.HistoryRepository;
import ru.kidesoft.desktop.domain.entity.history.History;
import ru.kidesoft.desktop.domain.exception.AppException;
import ru.kidesoft.desktop.domain.exception.DbException;

import java.util.List;

@Service
public class HistoryService {
    private final LoginService loginService;
    private final HistoryRepository historyRepository;
    private final Logger logger = LogManager.getLogger(HistoryService.class);
    public HistoryService(LoginService loginService, HistoryRepository historyRepository) {
        this.loginService = loginService;
        this.historyRepository = historyRepository;
    }

    public void saveHistory(History history) throws AppException {

        historyRepository.findHistoryByLoginAndOrderId(history.getLogin(), history.getOrderId()).ifPresent(
                (historyExists) -> {
                    history.setId(historyExists.getId());
                }
        );

        historyRepository.save(history);
    }

    public List<History> getListByLogin() throws AppException {
        try {
            var list = historyRepository.findAllByLogin(loginService.getCurrentLogin());
            logger.info("Список истории получен по логину {}. Длина: {}", loginService.getCurrentLogin(), list.size());
            return list;
        } catch (Exception e) {
            logger.error("Не удалось получить список историй", e);
            throw new DbException(e);
        }
    }

    public void saveByCurrentLogin(History history) throws AppException {
        var login = loginService.getCurrentLogin();
        history.setLogin(login);
        saveHistory(history);
    }
}
