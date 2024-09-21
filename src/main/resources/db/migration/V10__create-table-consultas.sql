create table consultas(
    id bigint not null auto_increment,
    paciente_id bigint not null,
    doctor_id bigint not null,
    fecha_consulta datetime not null,
    fecha_creacion datetime not null,

    primary key (id),

    constraint fk_consultas_paciente_id foreign key (paciente_id) references pacientes(id),
    constraint fk_consultas_doctor_id foreign key (doctor_id) references doctores(id)
);