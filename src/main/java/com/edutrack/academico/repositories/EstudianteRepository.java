package com.edutrack.academico.repositories;

import com.edutrack.academico.entities.EstadoEstudiante;
import com.edutrack.academico.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByEmail(String email);
    List<Estudiante> findByEstado(EstadoEstudiante estado);

    boolean existsByEmail(String email);

    boolean existsByNombreIgnoreCaseAndApellidoIgnoreCase(String nombre, String apellido);
}

