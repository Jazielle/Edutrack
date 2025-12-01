package com.edutrack.academico.controllers;

import com.edutrack.academico.common.ApiResponse;
import com.edutrack.academico.dtos.estudiante.EstudianteDTO;
import com.edutrack.academico.services.EstudianteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class EstudianteController {

    private final EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<ApiResponse<EstudianteDTO>> create(@Valid @RequestBody EstudianteDTO dto) {
        EstudianteDTO created = estudianteService.createEstudiante(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Estudiante creado correctamente", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EstudianteDTO>>> getAll() {
        List<EstudianteDTO> estudiantes = estudianteService.getEstudiantes();
        return ResponseEntity.ok(ApiResponse.ok("Listado de estudiantes", estudiantes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> getById(@PathVariable Long id) {
        EstudianteDTO estudiante = estudianteService.getEstudiante(id);
        return ResponseEntity.ok(ApiResponse.ok("Detalle del estudiante", estudiante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EstudianteDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteDTO dto
    ) {
        EstudianteDTO updated = estudianteService.updateEstudiante(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Estudiante actualizado", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        estudianteService.deleteEstudiante(id);
        return ResponseEntity.ok(ApiResponse.ok("Estudiante eliminado", null));
    }
}

