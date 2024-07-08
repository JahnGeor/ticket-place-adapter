package ru.kidesoft.desktop.repository.api;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.*;
import ru.kidesoft.desktop.infrastructure.port.api.web.ApiRepository;
import ru.kidesoft.desktop.infrastructure.port.api.dto.ClickDto;
import ru.kidesoft.desktop.infrastructure.port.api.dto.ProfileSessionDto;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.entity.order.SourceType;
import ru.kidesoft.desktop.domain.exception.ApiException;

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
            throw new ApiException(response.getStatusCode().value(), "Ошибка авторизации на удаленном сервере");
        }
    }

    @Override
    public ClickDto Click(int userId) throws ApiException {
        var rc = RestClient.builder().baseUrl(host).build();

        String tokenHeader = this.tokenType + " " + this.token;

        try {
            var body = rc.get()
                    .uri(click, userId)
                    .header("Authorization", tokenHeader).retrieve().onStatus(
                            HttpStatusCode::is5xxServerError,
                            (request, response) -> {
                                throw new HttpClientErrorException(response.getStatusCode(), "Ошибка при получении данных о последней операции с удаленного сервера");
                            }
                    ).onStatus(
                            HttpStatusCode::is4xxClientError,
                            (request, response) -> {
                                throw new HttpServerErrorException(response.getStatusCode(), "Ошибка при получении данных о последней операции с удаленного сервера");
                            }
                    ).body(ClickDto.class);

            if (body == null) {
                throw new ApiException(0, "Click body is null");
            }

            return body;
        } catch (HttpStatusCodeException e) {
            throw new ApiException(e.getStatusCode().value(), e.getMessage());
        }
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
                                throw new HttpClientErrorException(response.getStatusCode(), "Ошибка при получении данных с удаленного сервера");
                            }
                    ).onStatus(
                            HttpStatusCode::is4xxClientError,
                            (request, response) -> {
                                throw new HttpServerErrorException(response.getStatusCode(), "Ошибка при получении данных с удаленного сервера");
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
