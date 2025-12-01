package com.edutrack.academico.mappers;

import com.edutrack.academico.dtos.inscripcion.InscripcionDTO;
import com.edutrack.academico.entities.Inscripcion;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InscripcionMapper {

    InscripcionDTO toDto(Inscripcion inscripcion);

    @AfterMapping
    default void mapExtraData(Inscripcion inscripcion, @MappingTarget InscripcionDTO dto) {
        if (inscripcion.getEstudiante() != null) {
            dto.setEstudianteId(inscripcion.getEstudiante().getIdEstudiante());
            dto.setEstudianteNombre(
                    inscripcion.getEstudiante().getNombre() + " " + inscripcion.getEstudiante().getApellido()
            );
        }
        if (inscripcion.getCurso() != null) {
            dto.setCursoId(inscripcion.getCurso().getIdCurso());
            dto.setCursoNombre(inscripcion.getCurso().getNombre());
        }
    }
}

