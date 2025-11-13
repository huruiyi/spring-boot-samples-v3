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


INSERT INTO `security-db`.user (username, password)
VALUES ('user', '$2a$10$wY.aM2WHDbQ6CTquuq5ZHeTE1nf0yDYJi7S/OEvF4fhjRQE1.Dj52');
