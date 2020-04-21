create table if not exists zip
(
    postal_code varchar(4)  not null primary key,
    city        varchar(60) not null
);