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

