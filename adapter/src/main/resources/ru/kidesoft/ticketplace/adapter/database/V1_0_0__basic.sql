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

create table CLICK
(
    ID       UUID default RANDOM_UUID() not null,
    LOGIN_ID UUID                       not null
        constraint CLICK_PK_U
            unique,
    CLICK_ID INTEGER                    not null,
    ORDER_ID INTEGER                    not null,
    SOURCE_TYPE SMALLINT not null,
    CREATED_AT  TIMESTAMP WITH TIME ZONE not null,
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
    ERROR_STATUS         TINYINT                    not null,
    ERROR          CHARACTER VARYING,
    ORDER_ID       INTEGER                    not null,
    OPERATION_TYPE TINYINT                    not null,
    SOURCE_TYPE    TINYINT                    not null,
    STEP_TYPE      TINYINT                    not null,
    constraint HISTORY_PK
        primary key (ID),
    constraint HISTORY_PK_UNIQUE
        unique (LOGIN_ID, ORDER_ID, STEP_TYPE),
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
    ACTIVE       BOOLEAN                    not null,
    CREATED_AT   TIMESTAMP WITH TIME ZONE   not null,
    EXPIRED_AT   TIMESTAMP WITH TIME ZONE   not null,
    TOKEN_TYPE   CHARACTER VARYING(30)      not null,
    ACCESS_TOKEN CHARACTER VARYING          not null,
    constraint SESSION_PK
        primary key (ID),
    constraint SESSION_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade,
    constraint SESSION_LOGIN_ID_UNIQ
        unique (LOGIN_ID)
);

create table SETTING
(
    ID                      UUID    default RANDOM_UUID() not null,
    KKT_AUTO_RECONNECT      BOOLEAN           not null,
    KKT_DRIVER_PATH         CHARACTER VARYING not null,
    PRINTER_NAME            CHARACTER VARYING,
    PAGE_SIZE               TINYINT           not null,
    PAGE_ORIENTATION        TINYINT           not null,
    PRINT_CHECK             BOOLEAN           not null,
    PRINT_TICKET            BOOLEAN           not null,
    UPDATE_REPOSITORY_URL   CHARACTER VARYING not null,
    UPDATE_AUTOMATICALLY    BOOLEAN           not null,
    SERVER_REQUEST_TIMEOUT  NUMERIC           not null,
    SERVER_REQUEST_INTERVAL NUMERIC           not null,
    LOGIN_ID                UUID              not null,
    constraint SETTING_PK
        primary key (ID),
    constraint SETTING_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade
);
