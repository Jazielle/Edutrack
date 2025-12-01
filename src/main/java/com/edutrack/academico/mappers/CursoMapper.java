package com.edutrack.academico.mappers;

import com.edutrack.academico.dtos.curso.CursoDTO;
import com.edutrack.academico.entities.Curso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    CursoDTO toDto(Curso curso);
}

