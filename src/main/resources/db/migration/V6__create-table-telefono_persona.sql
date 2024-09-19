create table telefono_persona(
    id bigint auto_increment not null ,
    persona_id bigint not null,
    telefono_id bigint not null,
    principal boolean not null,

    primary key (id),

    constraint fk_telefono_persona_persona_id foreign key (persona_id) references personas(id),
    constraint fk_telefono_persona_telefono_id foreign key (telefono_id) references telefonos(id)
);