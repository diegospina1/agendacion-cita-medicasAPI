create table doctores(
    id bigint auto_increment not null,
    persona_id bigint not null,
    especialidad varchar(50) not null,

    primary key (id),

    constraint fk_doctores_persona_id foreign key (persona_id) references personas(id)
);