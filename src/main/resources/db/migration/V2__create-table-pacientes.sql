create table pacientes(
    id bigint auto_increment not null,
    persona_id bigint not null,

    primary key (id),

    constraint fk_pacientes_persona_id foreign key (persona_id) references personas(id)
);