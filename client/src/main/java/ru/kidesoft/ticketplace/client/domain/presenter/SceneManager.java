package ru.kidesoft.ticketplace.client.domain.presenter;

import ru.kidesoft.ticketplace.client.domain.executor.ThrowableLambda;

import java.util.function.Function;

public interface SceneManager {
    void openScene(ControllerType type) throws Exception;

    <T> void setUserData(ThrowableLambda.ThrowableSupplier<T> runnable) throws Exception;
}
