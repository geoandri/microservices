create table teams (
    id int8 generated by default as identity,
    description text,
    name text not null unique,
    primary key (id)
);

create sequence hibernate_sequence start 1 increment 1;