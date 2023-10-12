create database code_challenge;

create table province
(
    id   bigint auto_increment
        primary key,
    name varchar(255) null
);

create table address
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    province_id bigint       null,
    constraint UK_scab6kj8q2ixdf45obk4c9jk9
        unique (province_id),
    constraint FKf8x0jfwoo94op8u88og1ohdcn
        foreign key (province_id) references province (id)
);

create table county
(
    id          bigint auto_increment
        primary key,
    name        varchar(255) null,
    province_id bigint       null,
    constraint FK5htmnv99p7i819hs8gid194jl
        foreign key (province_id) references province (id)
);

create table city
(
    id        bigint auto_increment
        primary key,
    name      varchar(255) null,
    county_id bigint       null,
    constraint FKkftbhk3abjw6q9tmsmu6woosf
        foreign key (county_id) references county (id)
);

create table users
(
    id                bigint auto_increment
        primary key,
    age               int                                  null,
    confirmation_code varchar(255)                         null,
    date_of_birth     varchar(255)                         null,
    email             varchar(255)                         null,
    firstname         varchar(255)                         null,
    lastname          varchar(255)                         null,
    national_code     varchar(255)                         null,
    password          varchar(255)                         null,
    register_date     datetime(6)                          null,
    role              enum ('ADMIN', 'USER')               null,
    status            enum ('ACCEPTED', 'INACTIVE', 'NEW') null,
    address_id        bigint                               null,
    constraint UK_hbvhqvjgmhd5omxyo67ynvbyp
        unique (address_id),
    constraint `UniqueNationalCode&Email`
        unique (national_code, email),
    constraint FKditu6lr4ek16tkxtdsne0gxib
        foreign key (address_id) references address (id)
);

create table token
(
    id      bigint auto_increment
        primary key,
    expired bit             not null,
    revoked bit             not null,
    token   varchar(255)    null,
    type    enum ('BEARER') null,
    user_id bigint          null,
    constraint FKj8rfw4x0wjjyibfqq566j4qng
        foreign key (user_id) references users (id)
);

insert into province(name)
values('guilan');

insert into county(name,province_id)
values('rasht',1);

insert into city(county_id,name)
values(1,'khoshkebijar');

insert into address(description,province_id)
values('guilan, rasht',1);

insert into users(address_id,age,confirmation_code,date_of_birth,email,firstname,lastname,national_code,password,register_date,role,status)
values(1,21,499876,'2002-08-11','alireza@gmail.com','alireza','rastegar','5180182832','$2a$10$THKkB8g2Ok8V1WLtYh87iepYcErzoZhO7WEdC7LVKrj6fn33WTLPm','2023-10-12 13:43:08.998360','ADMIN','ACCEPTED')
