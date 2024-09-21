package com.jddi.project.dao.consulta;

import com.jddi.project.model.consulta.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRespository extends JpaRepository<Consulta, Long> {

}
