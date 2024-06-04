package ru.kidesoft.desktop.repository.api;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClient;
import ru.kidesoft.desktop.domain.dao.api.ApiRepository;
import ru.kidesoft.desktop.domain.dao.api.dto.ApiSetting;
import ru.kidesoft.desktop.domain.dao.api.dto.ClickDto;
import ru.kidesoft.desktop.domain.dao.api.dto.ProfileSessionDto;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.exception.ApiException;

import java.time.Duration;

public class ApiRepositoryImpl implements ApiRepository {
    final String host;
    final String token;
    final String tokenType;
    final Duration timeout;
    final String apiType;

    final String auth = "/api/auth/login?email={email}&password={password}"; // 1 пункт == email, 2 пункт == пароль //?email=%s&password=%s
    final String order = "/api/print-requests/by-user/%s"; // 1 пункт == id пользователя
    final String click = "/api/{type}/{id}"; // 1 пункт == тип операции (order, refund), 2 пункт == id операции

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
    public ClickDto Click(ApiSetting apiSetting, int userId) throws ApiException {
        return null;
    }

    @Override
    public Order Order(ApiSetting apiSetting, int orderId, OperationType operationType) throws ApiException {
        var rc = RestClient.builder().baseUrl(host).build();

        ResponseEntity<Order> response = rc.get().uri(order, operationType.getName(), orderId).retrieve().toEntity(Order.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new ApiException(response.getStatusCode().value(), "Error during getting order from remote server due incorrect status");
        }
    }
}


/*
*
* String authPathURI = "/api/auth/login?email=%s&password=%s";
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(
                        String.format(login.getUrl() + authPathURI, login.getEmail(), login.getPassword())
                )
        ).POST(HttpRequest.BodyPublishers.noBody()).build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException|InterruptedException e) {
            throw new WebException(e);
        }

        return switch (response.statusCode()) {
            case 200, 204 -> {
                try {
                    yield Deserializer.getObjectMapper().readValue(response.body(), AuthorizationDto.class);
                } catch (IOException e) {
                    throw new WebException(e);
                }
            }

            default -> throw new WebException(response.statusCode(), response.body());
        };
*
* */