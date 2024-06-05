package ru.kidesoft.desktop.repository.api;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.*;
import ru.kidesoft.desktop.domain.dao.api.ApiRepository;
import ru.kidesoft.desktop.domain.dao.api.dto.ApiSetting;
import ru.kidesoft.desktop.domain.dao.api.dto.ClickDto;
import ru.kidesoft.desktop.domain.dao.api.dto.ProfileSessionDto;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.exception.ApiException;

import java.io.IOException;
import java.rmi.RemoteException;
import java.time.Duration;

public class ApiRepositoryImpl implements ApiRepository {
    final String host;
    final String token;
    final String tokenType;
    final Duration timeout;
    final String apiType;

    final String auth = "/api/auth/login?email={email}&password={password}"; // 1 пункт == email, 2 пункт == пароль //?email=%s&password=%s
    final String click = "/api/print-requests/by-user/{id}"; // 1 пункт == id пользователя
    final String order = "/api/{type}/{id}"; // 1 пункт == тип операции (order, refund), 2 пункт == id операции

    public ApiRepositoryImpl(String host, String token, String tokenType, Duration timeout, String apiType) {
        this.host = host;
        this.token = token;
        this.tokenType = tokenType;
        this.timeout = timeout;
        this.apiType = apiType;
    }


    @Override
    public ProfileSessionDto Login(String user, String password) throws ApiException {
        var rc = RestClient.builder().baseUrl(host).build();

        ResponseEntity<ProfileSessionDto> response = rc.post().uri(auth, user, password).retrieve().toEntity(ProfileSessionDto.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ApiException(response.getStatusCode().value(), "Error during login on remote server due incorrect status");
        }
    }

    @Override
    public ClickDto Click(int userId) throws ApiException {
        return null;
    }

    @Override
    public Order Order(int orderId, SourceType sourceType) throws ApiException {
        var rc = RestClient.builder().baseUrl(host).build();

        String tokenHeader = this.tokenType + " " + this.token;

        try {
            var body = rc.get()
                    .uri(order, sourceType.getName(), orderId)
                    .header("Authorization", tokenHeader).retrieve().onStatus(
                            HttpStatusCode::is5xxServerError,
                            (request, response) -> {
                                throw new HttpClientErrorException(response.getStatusCode(), "Error during getting order from remote server due incorrect status");
                            }
                    ).onStatus(
                            HttpStatusCode::is4xxClientError,
                            (request, response) -> {
                                throw new HttpServerErrorException(response.getStatusCode(), "Error during getting order from remote server due incorrect status");
                            }
                    ).body(Order.class);

            if (body == null) {
                throw new ApiException(0, "Order body is null");
            }

            body.setSourceType(sourceType);

            return body;

        } catch (HttpStatusCodeException e) {
            throw new ApiException(e.getStatusCode().value(), e.getMessage());
        }
    }
}
