package ru.kidesoft.desktop.domain.dao.api;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.dao.api.dto.ApiSetting;
import ru.kidesoft.desktop.domain.dao.api.dto.ClickDto;
import ru.kidesoft.desktop.domain.dao.api.dto.ProfileSessionDto;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.exception.ApiException;

@Repository
public interface ApiRepository {
     ProfileSessionDto Login(String user, String password) throws ApiException;
     ClickDto Click(int userId) throws ApiException;
     Order Order(int orderId, SourceType sourceType) throws ApiException;
}
