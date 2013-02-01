
    drop table student cascade constraints;

    drop sequence hibernate_sequence;

    create table student (
        id number(10,0) not null,
        name varchar2(255),
        age number(10,0),
        gender boolean,
        birthday date,
        score double precision,
        primary key (id)
    );

    create sequence hibernate_sequence;
