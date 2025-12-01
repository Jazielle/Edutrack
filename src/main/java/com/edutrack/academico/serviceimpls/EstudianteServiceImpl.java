package com.edutrack.academico.serviceimpls;

import com.edutrack.academico.dtos.estudiante.EstudianteDTO;
import com.edutrack.academico.entities.EstadoEstudiante;
import com.edutrack.academico.entities.Estudiante;
import com.edutrack.academico.exceptions.ResourceNotFoundException;
import com.edutrack.academico.mappers.EstudianteMapper;
import com.edutrack.academico.repositories.EstudianteRepository;
import com.edutrack.academico.services.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final EstudianteMapper estudianteMapper;

    @Override
    public EstudianteDTO createEstudiante(EstudianteDTO dto) {
        boolean emailEnUso = estudianteRepository.existsByEmail(dto.getEmail());
        boolean nombreApellidoEnUso = estudianteRepository
                .existsByNombreIgnoreCaseAndApellidoIgnoreCase(dto.getNombre(), dto.getApellido());

        if (emailEnUso || nombreApellidoEnUso) {
            StringBuilder mensaje = new StringBuilder("No se puede registrar el estudiante: ");
            if (emailEnUso) {
                mensaje.append("el correo ya está en uso");
            }
            if (nombreApellidoEnUso) {
                if (emailEnUso) {
                    mensaje.append(" y ");
                }
                mensaje.append("ya existe un estudiante con el mismo nombre y apellido");
            }
            mensaje.append(".");
            throw new IllegalArgumentException(mensaje.toString());
        }

        Estudiante estudiante = estudianteMapper.toEntity(dto);
        Estudiante saved = estudianteRepository.save(estudiante);
        return estudianteMapper.toDto(saved);
    }

    @Override
    @Transactional
    public EstudianteDTO updateEstudiante(Long id, EstudianteDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));

        boolean emailCambiado = !estudiante.getEmail().equalsIgnoreCase(dto.getEmail());
        boolean nombreCambiado = !estudiante.getNombre().equalsIgnoreCase(dto.getNombre());
        boolean apellidoCambiado = !estudiante.getApellido().equalsIgnoreCase(dto.getApellido());

        boolean emailEnUso = emailCambiado && estudianteRepository.existsByEmail(dto.getEmail());
        boolean nombreApellidoEnUso = (nombreCambiado || apellidoCambiado)
                && estudianteRepository.existsByNombreIgnoreCaseAndApellidoIgnoreCase(dto.getNombre(), dto.getApellido());

        if (emailEnUso || nombreApellidoEnUso) {
            StringBuilder mensaje = new StringBuilder("No se puede actualizar el estudiante: ");
            if (emailEnUso) {
                mensaje.append("el correo ya está en uso");
            }
            if (nombreApellidoEnUso) {
                if (emailEnUso) {
                    mensaje.append(" y ");
                }
                mensaje.append("ya existe un estudiante con el mismo nombre y apellido");
            }
            mensaje.append(".");
            throw new IllegalArgumentException(mensaje.toString());
        }

        estudiante.setNombre(dto.getNombre());
        estudiante.setApellido(dto.getApellido());
        estudiante.setEmail(dto.getEmail());
        estudiante.setEstado(parseEstado(dto.getEstado()));

        return estudianteMapper.toDto(estudiante);
    }

    @Override
    public void deleteEstudiante(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
        estudianteRepository.delete(estudiante);
    }

    @Override
    public EstudianteDTO getEstudiante(Long id) {
        Estudiante estudiante = estudianteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estudiante no encontrado con id: " + id));
        return estudianteMapper.toDto(estudiante);
    }

    @Override
    public List<EstudianteDTO> getEstudiantes() {
        return estudianteRepository.findAll()
                .stream()
                .map(estudianteMapper::toDto)
                .toList();
    }

    private EstadoEstudiante parseEstado(String value) {
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

