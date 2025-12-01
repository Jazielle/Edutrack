package com.edutrack.academico.services;

import com.edutrack.academico.dtos.inscripcion.InscripcionDTO;

import java.util.List;

public interface InscripcionService {
    InscripcionDTO createInscripcion(InscripcionDTO dto);
    void deleteInscripcion(Long id);
    InscripcionDTO getInscripcion(Long id);
    List<InscripcionDTO> getInscripciones();
    List<InscripcionDTO> getInscripcionesPorEstudiante(Long estudianteId);
    List<InscripcionDTO> getInscripcionesPorCurso(Long cursoId);
}

