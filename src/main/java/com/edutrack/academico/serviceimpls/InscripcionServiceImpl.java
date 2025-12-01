package com.edutrack.academico.serviceimpls;

import com.edutrack.academico.dtos.inscripcion.InscripcionDTO;
import com.edutrack.academico.entities.Curso;
import com.edutrack.academico.entities.EstadoEstudiante;
import com.edutrack.academico.entities.Estudiante;
import com.edutrack.academico.entities.Inscripcion;
import com.edutrack.academico.exceptions.ResourceNotFoundException;
import com.edutrack.academico.mappers.InscripcionMapper;
import com.edutrack.academico.repositories.CursoRepository;
import com.edutrack.academico.repositories.EstudianteRepository;
import com.edutrack.academico.repositories.InscripcionRepository;
import com.edutrack.academico.services.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionServiceImpl implements InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final InscripcionMapper inscripcionMapper;

    @Override
    public InscripcionDTO createInscripcion(InscripcionDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + dto.getEstudianteId()));

        if (estudiante.getEstado() == EstadoEstudiante.INACTIVO) {
            throw new IllegalArgumentException("El estudiante está inactivo y no puede inscribirse.");
        }

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + dto.getCursoId()));

        inscripcionRepository.findByEstudianteAndCurso(estudiante, curso)
                .ifPresent(i -> {
                    throw new IllegalArgumentException("El estudiante ya está inscrito en este curso.");
                });

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());

        Inscripcion saved = inscripcionRepository.save(inscripcion);
        return inscripcionMapper.toDto(saved);
    }

    @Override
    public void deleteInscripcion(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
        inscripcionRepository.delete(inscripcion);
    }

    @Override
    public InscripcionDTO getInscripcion(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada con id: " + id));
        return inscripcionMapper.toDto(inscripcion);
    }

    @Override
    public List<InscripcionDTO> getInscripciones() {
        return inscripcionRepository.findAll()
                .stream()
                .map(inscripcionMapper::toDto)
                .toList();
    }

    @Override
    public List<InscripcionDTO> getInscripcionesPorEstudiante(Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + estudianteId));

        return inscripcionRepository.findByEstudiante(estudiante)
                .stream()
                .map(inscripcionMapper::toDto)
                .toList();
    }

    @Override
    public List<InscripcionDTO> getInscripcionesPorCurso(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + cursoId));

        return inscripcionRepository.findByCurso(curso)
                .stream()
                .map(inscripcionMapper::toDto)
                .toList();
    }
}

