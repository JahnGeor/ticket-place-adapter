package ru.kidesoft.ticketplace.client.domain.dao;

import ru.kidesoft.ticketplace.client.domain.models.exception.KktException;

public interface KktDao {
    void printXReport() throws KktException;
}
