package com.edutrack.academico.mappers;

import com.edutrack.academico.dtos.calificacion.CalificacionDTO;
import com.edutrack.academico.entities.Calificacion;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-12-01T08:43:03-0400",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CalificacionMapperImpl implements CalificacionMapper {

    @Override
    public CalificacionDTO toDto(Calificacion calificacion) {
        if ( calificacion == null ) {
            return null;
        }

        CalificacionDTO calificacionDTO = new CalificacionDTO();

        calificacionDTO.setIdCalificacion( calificacion.getIdCalificacion() );
        calificacionDTO.setNotaFinal( calificacion.getNotaFinal() );

        mapIds( calificacion, calificacionDTO );

        return calificacionDTO;
    }
}
