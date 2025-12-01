package com.edutrack.academico.repositories;

import com.edutrack.academico.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndProfesorIgnoreCase(String nombre, String profesor);
}

