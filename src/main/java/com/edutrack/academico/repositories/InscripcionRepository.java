package com.edutrack.academico.repositories;

import com.edutrack.academico.entities.Curso;
import com.edutrack.academico.entities.Estudiante;
import com.edutrack.academico.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    List<Inscripcion> findByEstudiante(Estudiante estudiante);
    List<Inscripcion> findByCurso(Curso curso);
    Optional<Inscripcion> findByEstudianteAndCurso(Estudiante estudiante, Curso curso);
}

