create table if not exists zip
(
    postal_code varchar(4)  not null primary key,
    city        varchar(60) not null
);

create table if not exists person_address
(
    person_address_id serial      not null primary key,
    person_street     varchar     not null,
    person_nr         varchar(10) not null,
    postal_code       varchar(4)  not null,
    foreign key (postal_code) references zip (postal_code)
);

CREATE SEQUENCE IF NOT EXISTS person_address_seq;