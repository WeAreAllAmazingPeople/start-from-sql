begin;

-- UNCOMMENT THESE LINES TO RESET THIS DB
--***************************************

--set schema 'parkshark';
--drop table if exists zip, person_address, person, allocations,
    --division, parking_lot, category, parking_address, phone, members, membership_level cascade;

--drop schema parkshark;
-- END UNCOMMENT
--**************

create schema if not exists parkshark;

set schema 'parkshark';

create table if not exists zip(
                                  postal_code varchar(4) not null primary key,
                                  city varchar(60) not null
);

create table if not  exists person_address(
                                              person_address_id serial not null primary key,
                                              person_street varchar not null,
                                              person_nr varchar (10) not null,
                                              postal_code varchar(4) not null,
                                              foreign key (postal_code) references zip(postal_code)
);

create table if not exists phone(
                                    phone_id serial not null primary key,
                                    phone_number varchar(15)
);

create table if not exists person(
                                     person_id uuid primary key DEFAULT uuid_generate_v4 (),	-- auto-generated UUID
    --person_id uuid not null primary key,	-- is an UUID
                                     firstname varchar(30) not null,
                                     lastname varchar(60) not null,
                                     address_id int not null,
                                     email varchar(100) not null,
                                     phone_fix int default null,
                                     phone_mobile int default null,
                                     foreign key (address_id) references person_address(person_address_id),
                                     foreign key (phone_fix) references phone(phone_id),
                                     foreign key (phone_mobile) references phone(phone_id)
);

create table if not exists division(
                                       division_id serial not null primary key,
                                       division_name varchar(80) not null,
                                       original_name varchar(80) not null,
                                       director_name varchar (100) not null,
                                       parent_division_id Integer default null,
                                       foreign key (parent_division_id) references division(division_id)
);

create table if not exists category(
                                       category_id serial not null primary key,
                                       category varchar(50) not null
);

create table if not exists parking_address(
                                              parking_address_id serial not null primary key,
                                              parking_street varchar not null,
                                              parking_nr varchar(10) not null,
                                              postal_code varchar(4) not null,
                                              foreign key (postal_code) references zip(postal_code)
);

create table if not exists parking_lot(
                                          pl_id serial not null primary key,
                                          pl_name varchar(100),
                                          category_id int not null,
                                          division_id int not null,
                                          max_capacity int not null
                                              check (max_capacity > 0), -- must be positive value
                                          contact_person_id uuid not null,  -- UUID of a person
                                          address_id int not null,
                                          price_hour money not null,
                                          foreign key (category_id) references category(category_id),
                                          foreign key (division_id) references division(division_id),
                                          foreign key (contact_person_id) references person(person_id),
                                          foreign key (address_id) references parking_address(parking_address_id)
);

create table if not exists membership_level(
                                               level_id int not null primary key,
                                               level_name varchar(10) not null,
                                               monthly_cost money not null,
                                               reduction_pct numeric(5,2) not null
                                                   check(reduction_pct>=0 and reduction_pct<=100),
                                               max_alloc_time int not null
                                                   check(max_alloc_time > 0)
);

create table if not exists members(
                                      member_id uuid not null primary key DEFAULT uuid_generate_v4 (),  --auto-generated UUID ,
                                      firstname varchar(30) not null,
                                      lastname varchar(60) not null,
                                      address_id int not null,
                                      email varchar(100) not null,
                                      phone_id int not null,
                                      licence_plate varchar(15) not null,
                                      licence_plate_country varchar(3) not null,
                                      registration_date date not null,
                                      level_id int not null default '1', 						-- default level BRONZE
                                      foreign key (level_id) references membership_level(level_id),
                                      foreign key (address_id) references person_address(person_address_id),
                                      foreign key (phone_id) references phone(phone_id)
);

create table if not exists allocations(
                                          allocation_id uuid not null primary key DEFAULT uuid_generate_v4 (),  --auto-generated UUID
                                          pl_id int not null,
                                          member_id uuid not null,
                                          licence_plate varchar(15) not null,
                                          time_in timestamp not null,
                                          time_out timestamp default null,
                                          amount_due money default null,
                                          is_active boolean default true,
                                          foreign key (pl_id) references parking_lot(pl_id),
                                          foreign key (member_id) references members(member_id)
);

commit;