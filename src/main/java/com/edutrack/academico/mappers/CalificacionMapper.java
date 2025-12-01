package com.edutrack.academico.mappers;

import com.edutrack.academico.dtos.calificacion.CalificacionDTO;
import com.edutrack.academico.entities.Calificacion;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CalificacionMapper {

    CalificacionDTO toDto(Calificacion calificacion);

    @AfterMapping
    default void mapIds(Calificacion calificacion, @MappingTarget CalificacionDTO dto) {
        if (calificacion.getEstudiante() != null) {
            dto.setEstudianteId(calificacion.getEstudiante().getIdEstudiante());
            dto.setEstudianteNombre(
                    calificacion.getEstudiante().getNombre() + " " + calificacion.getEstudiante().getApellido()
            );
        }
        if (calificacion.getCurso() != null) {
            dto.setCursoId(calificacion.getCurso().getIdCurso());
            dto.setCursoNombre(calificacion.getCurso().getNombre());
        }
    }
}

