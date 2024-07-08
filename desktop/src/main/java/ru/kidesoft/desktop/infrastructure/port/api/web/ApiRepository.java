package ru.kidesoft.desktop.infrastructure.port.api.web;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.infrastructure.port.api.dto.ProfileSessionDto;
import ru.kidesoft.desktop.infrastructure.port.api.dto.ClickDto;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.exception.ApiException;

@Repository
public interface ApiRepository {
     ProfileSessionDto Login(String user, String password) throws ApiException;
     ClickDto Click(int userId) throws ApiException;
     Order Order(int orderId, SourceType sourceType) throws ApiException;
}
