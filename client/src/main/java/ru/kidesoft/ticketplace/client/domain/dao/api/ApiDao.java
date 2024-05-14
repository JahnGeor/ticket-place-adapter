package ru.kidesoft.ticketplace.client.domain.dao.api;

import ru.kidesoft.ticketplace.client.domain.dao.api.dto.authorization.AuthorizationDto;
import ru.kidesoft.ticketplace.client.domain.models.entities.login.Login;
import ru.kidesoft.ticketplace.client.domain.models.exception.WebException;

public interface ApiDao {

    AuthorizationDto authorization(Login login) throws WebException;
}
