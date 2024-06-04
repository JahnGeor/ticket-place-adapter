package ru.kidesoft.desktop.repository.api;

import org.springframework.stereotype.Repository;
import ru.kidesoft.desktop.domain.dao.api.ApiRepository;
import ru.kidesoft.desktop.domain.dao.api.ApiRepositoryFactory;

import java.time.Duration;

@Repository
public class ApiRepositoryFactoryImpl implements ApiRepositoryFactory {

    String host;
    String token;
    String tokenType;
    Duration timeout;
    String apiType;
    // TODO: Превратить в Enum ApiType

    @Override
    public ApiRepositoryFactory setHost(String host) {
        this.host = host;
        return this;
    }

    @Override
    public ApiRepositoryFactory setToken(String token) {
        this.token = token;
        return this;
    }

    @Override
    public ApiRepositoryFactory setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    @Override
    public ApiRepositoryFactory setTimeout(Duration timeout) {
        this.timeout = timeout;
        return this;
    }

    @Override
    public ApiRepositoryFactory setType(String type) {
        this.apiType = type;
        return this;
    }

    @Override
    public ApiRepository build() {
        return new ApiRepositoryImpl(host, token, tokenType, timeout, apiType);
    }
}
