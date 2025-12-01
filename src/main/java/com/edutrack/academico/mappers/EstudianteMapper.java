package com.edutrack.academico.mappers;

import com.edutrack.academico.dtos.estudiante.EstudianteDTO;
import com.edutrack.academico.entities.EstadoEstudiante;
import com.edutrack.academico.entities.Estudiante;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EstudianteMapper {

    EstudianteDTO toDto(Estudiante estudiante);

    @Mapping(target = "estado", expression = "java(toEstadoEnum(dto.getEstado()))")
    Estudiante toEntity(EstudianteDTO dto);

    @AfterMapping
    default void mapEstado(Estudiante estudiante, @MappingTarget EstudianteDTO dto) {
        if (estudiante.getEstado() != null) {
            dto.setEstado(estudiante.getEstado().name());
        }
    }

    default EstadoEstudiante toEstadoEnum(String value) {
        if (value == null || value.isBlank()) {
            return EstadoEstudiante.ACTIVO;
        }
        try {
            return EstadoEstudiante.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return EstadoEstudiante.ACTIVO;
        }
    }
}

