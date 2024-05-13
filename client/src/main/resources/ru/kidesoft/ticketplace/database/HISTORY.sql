create table HISTORY
(
    ID             UUID default RANDOM_UUID() not null,
    LOGIN_ID       UUID                       not null,
    CREATED_AT     TIMESTAMP WITH TIME ZONE   not null,
    STATUS         TINYINT                    not null,
    ERROR          CHARACTER VARYING,
    ORDER_ID       INTEGER                    not null,
    OPERATION_TYPE TINYINT                    not null,
    constraint HISTORY_PK
        primary key (ID),
    constraint HISTORY_PK_UNIQUE
        unique (LOGIN_ID, ORDER_ID),
    constraint HISTORY_LOGIN_ID_FK
        foreign key (LOGIN_ID) references LOGIN
            on update cascade on delete cascade
);

