create table email_persona(
    id bigint auto_increment not null,
    persona_id bigint not null,
    email_id bigint not null,
    principal boolean not null,

    primary key (id),

    constraint fk_email_persona_persona_id foreign key (persona_id) references personas(id),
    constraint fk_email_persona_email_id foreign key (email_id) references email(id)
);