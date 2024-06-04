package ru.kidesoft.desktop.domain.dao.api;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.dao.api.dto.ApiSetting;
import ru.kidesoft.desktop.domain.dao.api.dto.ClickDto;
import ru.kidesoft.desktop.domain.dao.api.dto.ProfileSessionDto;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.exception.ApiException;

@Repository
public interface ApiRepository {
     ProfileSessionDto Login(String user, String password) throws ApiException;
     ClickDto Click(ApiSetting apiSetting, int userId) throws ApiException;
     Order Order(ApiSetting apiSetting, int orderId, OperationType operationType) throws ApiException;
}
