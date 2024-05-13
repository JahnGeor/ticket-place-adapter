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

