package com.edutrack.academico.services;

import com.edutrack.academico.dtos.curso.CursoDTO;

import java.util.List;

public interface CursoService {
    CursoDTO createCurso(CursoDTO dto);
    CursoDTO updateCurso(Long id, CursoDTO dto);
    void deleteCurso(Long id);
    CursoDTO getCurso(Long id);
    List<CursoDTO> getCursos();
    List<CursoDTO> getCursosPorProfesor(Long profesorId);
}

