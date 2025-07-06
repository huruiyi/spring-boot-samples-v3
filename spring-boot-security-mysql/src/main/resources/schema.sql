create table persistent_logins
(
    username  varchar(64) not null,
    series    varchar(64) not null
        primary key,
    token     varchar(64) not null,
    last_used timestamp   not null
);

create table user
(
    id       bigint auto_increment
        primary key,
    username varchar(100) null,
    password varchar(300) null
);

