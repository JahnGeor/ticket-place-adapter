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

