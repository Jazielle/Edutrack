package com.edutrack.academico.serviceimpls;

import com.edutrack.academico.dtos.calificacion.CalificacionDTO;
import com.edutrack.academico.entities.*;
import com.edutrack.academico.exceptions.ResourceNotFoundException;
import com.edutrack.academico.mappers.CalificacionMapper;
import com.edutrack.academico.repositories.CalificacionRepository;
import com.edutrack.academico.repositories.CursoRepository;
import com.edutrack.academico.repositories.EstudianteRepository;
import com.edutrack.academico.repositories.InscripcionRepository;
import com.edutrack.academico.services.CalificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalificacionServiceImpl implements CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;
    private final InscripcionRepository inscripcionRepository;
    private final CalificacionMapper calificacionMapper;

    @Override
    public CalificacionDTO createCalificacion(CalificacionDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + dto.getEstudianteId()));

        if (estudiante.getEstado() == EstadoEstudiante.INACTIVO) {
            throw new IllegalArgumentException("El estudiante está inactivo.");
        }

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + dto.getCursoId()));

        inscripcionRepository.findByEstudianteAndCurso(estudiante, curso)
                .orElseThrow(() -> new IllegalArgumentException("El estudiante no está inscrito en este curso."));

        Calificacion calificacion = new Calificacion();
        calificacion.setEstudiante(estudiante);
        calificacion.setCurso(curso);
        calificacion.setNotaFinal(dto.getNotaFinal());

        Calificacion saved = calificacionRepository.save(calificacion);
        return calificacionMapper.toDto(saved);
    }

    @Override
    public CalificacionDTO updateCalificacion(Long id, CalificacionDTO dto) {
        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calificación no encontrada con id: " + id));

        calificacion.setNotaFinal(dto.getNotaFinal());
        Calificacion updated = calificacionRepository.save(calificacion);
        return calificacionMapper.toDto(updated);
    }

    @Override
    public void deleteCalificacion(Long id) {
        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calificación no encontrada con id: " + id));
        calificacionRepository.delete(calificacion);
    }

    @Override
    public CalificacionDTO getCalificacion(Long id) {
        Calificacion calificacion = calificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calificación no encontrada con id: " + id));
        return calificacionMapper.toDto(calificacion);
    }

    @Override
    public List<CalificacionDTO> getCalificaciones() {
        return calificacionRepository.findAll()
                .stream()
                .map(calificacionMapper::toDto)
                .toList();
    }

    @Override
    public List<CalificacionDTO> getCalificacionesPorEstudiante(Long estudianteId) {
        Estudiante estudiante = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + estudianteId));

        return calificacionRepository.findByEstudiante(estudiante)
                .stream()
                .map(calificacionMapper::toDto)
                .toList();
    }

    @Override
    public List<CalificacionDTO> getCalificacionesPorCurso(Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + cursoId));

        return calificacionRepository.findByCurso(curso)
                .stream()
                .map(calificacionMapper::toDto)
                .toList();
    }
}

