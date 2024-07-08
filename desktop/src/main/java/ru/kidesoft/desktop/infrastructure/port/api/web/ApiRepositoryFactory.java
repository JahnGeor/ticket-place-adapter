package ru.kidesoft.desktop.infrastructure.port.api.web;

import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public interface ApiRepositoryFactory {
    ApiRepositoryFactory setType(String type);
    ApiRepositoryFactory setHost(String host);
    ApiRepositoryFactory setToken(String token);
    ApiRepositoryFactory setTokenType(String tokenType);
    ApiRepositoryFactory setTimeout(Duration timeout);
    ApiRepository build();
}
