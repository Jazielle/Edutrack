package com.edutrack.academico.mappers;

import com.edutrack.academico.dtos.inscripcion.InscripcionDTO;
import com.edutrack.academico.entities.Inscripcion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-01T08:43:04-0400",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class InscripcionMapperImpl implements InscripcionMapper {

    @Override
    public InscripcionDTO toDto(Inscripcion inscripcion) {
        if ( inscripcion == null ) {
            return null;
        }

        InscripcionDTO inscripcionDTO = new InscripcionDTO();

        inscripcionDTO.setIdInscripcion( inscripcion.getIdInscripcion() );
        inscripcionDTO.setFecha( inscripcion.getFecha() );

        mapExtraData( inscripcion, inscripcionDTO );

        return inscripcionDTO;
    }
}
