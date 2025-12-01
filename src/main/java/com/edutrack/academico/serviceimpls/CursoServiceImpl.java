package com.edutrack.academico.serviceimpls;

import com.edutrack.academico.dtos.curso.CursoDTO;
import com.edutrack.academico.entities.Curso;
import com.edutrack.academico.exceptions.ResourceNotFoundException;
import com.edutrack.academico.mappers.CursoMapper;
import com.edutrack.academico.repositories.CursoRepository;
import com.edutrack.academico.services.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;

    @Override
    public CursoDTO createCurso(CursoDTO dto) {
        boolean nombreEnUso = cursoRepository.existsByNombreIgnoreCase(dto.getNombre());
        boolean nombreProfesorEnUso = cursoRepository
                .existsByNombreIgnoreCaseAndProfesorIgnoreCase(dto.getNombre(), dto.getProfesor());

        if (nombreEnUso || nombreProfesorEnUso) {
            StringBuilder mensaje = new StringBuilder("No se puede registrar el curso: ");
            if (nombreEnUso) {
                mensaje.append("el nombre del curso ya está en uso");
            }
            if (nombreProfesorEnUso) {
                if (nombreEnUso) {
                    mensaje.append(" y ");
                }
                mensaje.append("ya existe un curso con el mismo nombre y profesor");
            }
            mensaje.append(".");
            throw new IllegalArgumentException(mensaje.toString());
        }

        Curso curso = new Curso();
        curso.setNombre(dto.getNombre());
        curso.setProfesor(dto.getProfesor());

        Curso saved = cursoRepository.save(curso);
        return cursoMapper.toDto(saved);
    }

    @Override
    public CursoDTO updateCurso(Long id, CursoDTO dto) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));

        boolean nombreCambiado = !curso.getNombre().equalsIgnoreCase(dto.getNombre());
        boolean profesorCambiado = !curso.getProfesor().equalsIgnoreCase(dto.getProfesor());

        boolean nombreEnUso = nombreCambiado && cursoRepository.existsByNombreIgnoreCase(dto.getNombre());
        boolean nombreProfesorEnUso = (nombreCambiado || profesorCambiado)
                && cursoRepository.existsByNombreIgnoreCaseAndProfesorIgnoreCase(dto.getNombre(), dto.getProfesor());

        if (nombreEnUso || nombreProfesorEnUso) {
            StringBuilder mensaje = new StringBuilder("No se puede actualizar el curso: ");
            if (nombreEnUso) {
                mensaje.append("el nombre del curso ya está en uso");
            }
            if (nombreProfesorEnUso) {
                if (nombreEnUso) {
                    mensaje.append(" y ");
                }
                mensaje.append("ya existe un curso con el mismo nombre y profesor");
            }
            mensaje.append(".");
            throw new IllegalArgumentException(mensaje.toString());
        }

        curso.setNombre(dto.getNombre());
        curso.setProfesor(dto.getProfesor());

        Curso updated = cursoRepository.save(curso);
        return cursoMapper.toDto(updated);
    }

    @Override
    public void deleteCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
        cursoRepository.delete(curso);
    }

    @Override
    public CursoDTO getCurso(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado con id: " + id));
        return cursoMapper.toDto(curso);
    }

    @Override
    public List<CursoDTO> getCursos() {
        return cursoRepository.findAll()
                .stream()
                .map(cursoMapper::toDto)
                .toList();
    }

    @Override
    public List<CursoDTO> getCursosPorProfesor(Long profesorId) {
        throw new UnsupportedOperationException("Filtrar cursos por profesor ya no está soportado");
    }
}

