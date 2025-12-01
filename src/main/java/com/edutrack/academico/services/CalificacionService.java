package com.edutrack.academico.services;

import com.edutrack.academico.dtos.calificacion.CalificacionDTO;

import java.util.List;

public interface CalificacionService {
    CalificacionDTO createCalificacion(CalificacionDTO dto);
    CalificacionDTO updateCalificacion(Long id, CalificacionDTO dto);
    void deleteCalificacion(Long id);
    CalificacionDTO getCalificacion(Long id);
    List<CalificacionDTO> getCalificaciones();
    List<CalificacionDTO> getCalificacionesPorEstudiante(Long estudianteId);
    List<CalificacionDTO> getCalificacionesPorCurso(Long cursoId);
}

