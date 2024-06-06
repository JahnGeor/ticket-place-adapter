package ru.kidesoft.desktop.domain.dao.kkt;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.entity.State;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.exception.KktException;

import java.time.ZonedDateTime;

@Repository
public interface KktRepository {
    KktRepository setConnection(KktSetting kktSetting) throws KktException;
    KktPrinter getPrinter() throws KktException;
    KktSystem getSystem() throws KktException;
}
