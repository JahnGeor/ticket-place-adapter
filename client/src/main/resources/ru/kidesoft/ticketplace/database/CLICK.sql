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

