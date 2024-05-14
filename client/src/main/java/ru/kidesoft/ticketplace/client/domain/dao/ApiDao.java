package ru.kidesoft.ticketplace.client.domain.dao;

import ru.kidesoft.ticketplace.client.domain.dao.dto.AuthorizationDto;
import ru.kidesoft.ticketplace.client.domain.models.exception.WebException;
import ru.kidesoft.ticketplace.client.domain.presenter.dto.Login;

public interface ApiDao {
    AuthorizationDto authorization(Login login) throws WebException;
}
