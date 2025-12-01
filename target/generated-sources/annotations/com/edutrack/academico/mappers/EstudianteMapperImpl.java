package com.edutrack.academico.mappers;

import com.edutrack.academico.dtos.estudiante.EstudianteDTO;
import com.edutrack.academico.entities.Estudiante;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-01T08:43:04-0400",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class EstudianteMapperImpl implements EstudianteMapper {

    @Override
    public EstudianteDTO toDto(Estudiante estudiante) {
        if ( estudiante == null ) {
            return null;
        }

        EstudianteDTO estudianteDTO = new EstudianteDTO();

        estudianteDTO.setIdEstudiante( estudiante.getIdEstudiante() );
        estudianteDTO.setNombre( estudiante.getNombre() );
        estudianteDTO.setApellido( estudiante.getApellido() );
        estudianteDTO.setEmail( estudiante.getEmail() );
        if ( estudiante.getEstado() != null ) {
            estudianteDTO.setEstado( estudiante.getEstado().name() );
        }

        mapEstado( estudiante, estudianteDTO );

        return estudianteDTO;
    }

    @Override
    public Estudiante toEntity(EstudianteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Estudiante estudiante = new Estudiante();

        estudiante.setIdEstudiante( dto.getIdEstudiante() );
        estudiante.setNombre( dto.getNombre() );
        estudiante.setApellido( dto.getApellido() );
        estudiante.setEmail( dto.getEmail() );

        estudiante.setEstado( toEstadoEnum(dto.getEstado()) );

        return estudiante;
    }
}
