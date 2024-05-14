package ru.kidesoft.ticketplace.client.repository.api.ticketplace;

import ru.kidesoft.ticketplace.client.domain.dao.api.ApiDao;
import ru.kidesoft.ticketplace.client.domain.dao.api.dto.authorization.AuthorizationDto;
import ru.kidesoft.ticketplace.client.domain.dao.api.dto.authorization.Deserializer;
import ru.kidesoft.ticketplace.client.domain.models.entities.login.Login;
import ru.kidesoft.ticketplace.client.domain.models.exception.WebException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TicketPlaceApiRepository implements ApiDao {
    HttpClient client = HttpClient.newHttpClient();
    @Override
    public AuthorizationDto authorization(Login login) throws WebException {
        String authPathURI = "/api/auth/login?email=%s&password=%s";
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
    }
}
