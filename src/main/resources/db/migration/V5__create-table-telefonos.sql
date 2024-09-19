create table telefonos(
    id bigint not null auto_increment,
    telefono varchar(10) not null unique,

    primary key (id)
);