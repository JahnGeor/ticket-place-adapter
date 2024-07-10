create table LOGIN
(
    ID       UUID default RANDOM_UUID() not null,
    EMAIL    CHARACTER VARYING          not null,
    PASSWORD CHARACTER VARYING          not null,
    URL      CHARACTER VARYING          not null,
    constraint LOGIN_PK
        primary key (ID),
    constraint LOGIN_PK_UNIQUE
        unique (EMAIL, URL)
);

create table CONSTANT
(
    NAME CHARACTER VARYING not null,
    VAL  CHARACTER VARYING,
    constraint CONSTANT_PK
        primary key (NAME)
);

INSERT INTO CONSTANT (NAME, VAL) VALUES ('ACTIVE_LOGIN_UUID', null);
INSERT INTO CONSTANT (NAME, VAL) VALUES ('DEFAULT_URL', 'https://ticket-place.ru');

create table CLICK
(
    ID       UUID default RANDOM_UUID() not null,
    LOGIN_ID UUID                       not null
        constraint CLICK_PK_U
            unique,
    CLICK_ID INTEGER                    not null,
    constraint CLICK_PK
        primary key (ID),
    constraint CLICK_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade
);

create table HISTORY
(
    ID             UUID default RANDOM_UUID() not null,
    LOGIN_ID       UUID                       not null,
    CREATED_AT     TIMESTAMP WITH TIME ZONE   not null,
    STATUS         TINYINT                    not null,
    ERROR          CHARACTER VARYING,
    ORDER_ID       INTEGER                    not null,
    OPERATION_TYPE TINYINT                    not null,
    SOURCE_TYPE TINYINT                    not null,
    constraint HISTORY_PK
        primary key (ID),
    constraint HISTORY_PK_UNIQUE
        unique (LOGIN_ID, ORDER_ID),
    constraint HISTORY_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade
);

create table PROFILE
(
    ID       UUID default RANDOM_UUID() not null,
    LOGIN_ID UUID                       not null,
    INN      BIGINT                     not null,
    ROLE     TINYINT,
    AVATAR   CHARACTER VARYING,
    FULLNAME CHARACTER VARYING          not null,
    USERNAME CHARACTER VARYING          not null,
    USER_ID  INTEGER                    not null,
    constraint PROFILE_PK
        primary key (ID),
    constraint PROFILE_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade
);

create table SESSION
(
    ID           UUID default RANDOM_UUID() not null,
    LOGIN_ID     UUID                       not null,
    CREATED_AT   TIMESTAMP WITH TIME ZONE   not null,
    EXPIRED_AT   TIMESTAMP WITH TIME ZONE   not null,
    TOKEN_TYPE   CHARACTER VARYING(30)      not null,
    ACCESS_TOKEN CHARACTER VARYING          not null,
    constraint SESSION_PK
        primary key (ID),
    constraint SESSION_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade
);

create table SETTING
(
    ID                      UUID              not null, --default RANDOM_UUID()
    KKT_AUTO_RECONNECT      BOOLEAN           not null, --default TRUE
    KKT_DRIVER_PATH         CHARACTER VARYING not null, --default './drivers/kkt/10.9.5.0/'
    PRINTER_NAME            CHARACTER VARYING,  --default ''
    PAGE_SIZE               TINYINT           not null, --default 1
    PAGE_ORIENTATION        TINYINT           not null, --default 1
    PRINT_CHECK             BOOLEAN           not null, --default TRUE
    PRINT_TICKET            BOOLEAN           not null, --default TRUE
    UPDATE_REPOSITORY_URL   CHARACTER VARYING not null, --default 'https://example.com'
    UPDATE_AUTOMATICALLY    BOOLEAN           not null, --default FALSE
    SERVER_REQUEST_TIMEOUT  NUMERIC           not null, --default 10000000000
    SERVER_REQUEST_INTERVAL NUMERIC                           not null, --default 5000000000
    LOGIN_ID                UUID                                                not null,
    constraint SETTING_PK
        primary key (ID),
    constraint SETTING_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade
);
