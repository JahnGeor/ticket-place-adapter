package ru.kidesoft.ticketplace.client.domain.interactor.usecase;

import ru.kidesoft.ticketplace.client.domain.dao.DatabaseDao;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.History;

import java.util.Optional;

public class HistoryUsecase {

    DatabaseDao databaseDao;

    public History getHistory() {
        return null;
    }

    public HistoryUsecase(DatabaseDao databaseDao) {
        this.databaseDao = databaseDao;
    }
}
