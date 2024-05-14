package ru.kidesoft.ticketplace.client.domain.models.entities.setting;

public final class UpdateBuilder {
    private String repository;
    private Boolean auto;

    private UpdateBuilder() {
    }

    public static UpdateBuilder anUpdate() {
        return new UpdateBuilder();
    }

    public UpdateBuilder withRepository(String repository) {
        this.repository = repository;
        return this;
    }

    public UpdateBuilder withAuto(Boolean auto) {
        this.auto = auto;
        return this;
    }

    public Update build() {
        Update update = new Update();
        update.setRepository(repository);
        update.setAuto(auto);
        return update;
    }
}
