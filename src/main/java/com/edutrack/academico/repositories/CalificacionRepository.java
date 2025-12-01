package com.edutrack.academico.repositories;

import com.edutrack.academico.entities.Calificacion;
import com.edutrack.academico.entities.Curso;
import com.edutrack.academico.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    List<Calificacion> findByEstudiante(Estudiante estudiante);
    List<Calificacion> findByCurso(Curso curso);
    Optional<Calificacion> findByEstudianteAndCurso(Estudiante estudiante, Curso curso);
}

