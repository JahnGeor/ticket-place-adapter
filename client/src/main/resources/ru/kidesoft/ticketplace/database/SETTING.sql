create table SETTING
(
    ID                      UUID              default RANDOM_UUID()             not null,
    KKT_AUTO_RECONNECT      BOOLEAN           default TRUE                      not null,
    KKT_DRIVER_PATH         CHARACTER VARYING default './drivers/kkt/10.9.5.0/' not null,
    PRINTER_NAME            CHARACTER VARYING default '',
    PAGE_SIZE               TINYINT           default 1                         not null,
    PAGE_ORIENTATION        TINYINT           default 1                         not null,
    PRINT_CHECK             BOOLEAN           default TRUE                      not null,
    PRINT_TICKET            BOOLEAN           default TRUE                      not null,
    UPDATE_REPOSITORY_URL   CHARACTER VARYING default 'https://example.com'     not null,
    UPDATE_AUTOMATICALLY    BOOLEAN           default FALSE                     not null,
    SERVER_REQUEST_TIMEOUT  NUMERIC           default 10000000000               not null,
    SERVER_REQUEST_INTERVAL NUMERIC           default 5000000000                not null,
    LOGIN_ID                UUID                                                not null,
    constraint SETTING_PK
        primary key (ID),
    constraint SETTING_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade
);

