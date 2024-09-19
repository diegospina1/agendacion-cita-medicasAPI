create table personas(
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    apellido varchar(100) not null,
    documento varchar(10) not null unique,
    activo boolean not null,

    primary key (id)
);