package ru.kidesoft.desktop.infrastructure.port.api.kkt;

import org.springframework.stereotype.Repository;
import ru.atol.drivers10.fptr.IFptr;
import ru.kidesoft.desktop.domain.exception.KktException;

@Repository
public interface KktRepository {
    KktRepository setConnection(KktSetting kktSetting) throws KktException;

    KktRepository setConnection() throws KktException;

    KktRepository openConnection() throws KktException;

    KktRepository closeConnection() throws KktException;



    IFptr getFptr() throws KktException;
}
