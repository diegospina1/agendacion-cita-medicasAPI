create table email(
    id bigint not null auto_increment,
    email varchar(150) not null unique,

    primary key (id)
);