package com.edutrack.academico.services;

import com.edutrack.academico.dtos.estudiante.EstudianteDTO;

import java.util.List;

public interface EstudianteService {
    EstudianteDTO createEstudiante(EstudianteDTO dto);
    EstudianteDTO updateEstudiante(Long id, EstudianteDTO dto);
    void deleteEstudiante(Long id);
    EstudianteDTO getEstudiante(Long id);
    List<EstudianteDTO> getEstudiantes();
}

