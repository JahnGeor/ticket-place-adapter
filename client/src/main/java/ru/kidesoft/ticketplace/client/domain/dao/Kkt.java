package ru.kidesoft.ticketplace.client.domain.dao;

import ru.kidesoft.ticketplace.client.domain.models.exception.KktException;

public interface Kkt {
    void printXReport() throws KktException;
}
