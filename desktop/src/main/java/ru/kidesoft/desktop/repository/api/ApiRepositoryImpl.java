package ru.kidesoft.desktop.repository.api;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.dao.ApiRepository;
import ru.kidesoft.desktop.domain.dao.ApiSetting;
import ru.kidesoft.desktop.domain.dao.ClickDto;
import ru.kidesoft.desktop.domain.dao.LoginDto;
import ru.kidesoft.desktop.domain.entity.login.Login;
import ru.kidesoft.desktop.domain.entity.order.OperationType;
import ru.kidesoft.desktop.domain.entity.order.Order;
import ru.kidesoft.desktop.domain.exception.ApiException;

import java.net.http.HttpClient;

@Repository
@AllArgsConstructor
public class ApiRepositoryImpl implements ApiRepository {
    final String auth = "/api/auth/login?email=%s&password=%s"; // 1 пункт == email, 2 пункт == пароль
    final String order = "/api/print-requests/by-user/%s"; // 1 пункт == id пользователя
    final String click = "/api/%s/%d"; // 1 пункт == тип операции (order, refund), 2 пункт == id операции

    final HttpClient client = HttpClient.newHttpClient();


    @Override
    public LoginDto Login(Login login) throws ApiException {
        return null;
    }

    @Override
    public ClickDto Click(ApiSetting apiSetting, int userId) throws ApiException {
        return null;
    }

    @Override
    public Order Order(ApiSetting apiSetting, int orderId, OperationType operationType) throws ApiException {
        return null;
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