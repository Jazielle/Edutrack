package com.edutrack.academico.controllers;

import com.edutrack.academico.common.ApiResponse;
import com.edutrack.academico.dtos.calificacion.CalificacionDTO;
import com.edutrack.academico.services.CalificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CalificacionController {

    private final CalificacionService calificacionService;

    @PostMapping
    public ResponseEntity<ApiResponse<CalificacionDTO>> create(@Valid @RequestBody CalificacionDTO dto) {
        CalificacionDTO created = calificacionService.createCalificacion(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Calificación registrada", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CalificacionDTO>>> getAll() {
        List<CalificacionDTO> calificaciones = calificacionService.getCalificaciones();
        return ResponseEntity.ok(ApiResponse.ok("Listado de calificaciones", calificaciones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CalificacionDTO>> getById(@PathVariable Long id) {
        CalificacionDTO calificacion = calificacionService.getCalificacion(id);
        return ResponseEntity.ok(ApiResponse.ok("Detalle de la calificación", calificacion));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<ApiResponse<List<CalificacionDTO>>> getByEstudiante(@PathVariable Long estudianteId) {
        List<CalificacionDTO> calificaciones = calificacionService.getCalificacionesPorEstudiante(estudianteId);
        return ResponseEntity.ok(ApiResponse.ok("Calificaciones del estudiante", calificaciones));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<List<CalificacionDTO>>> getByCurso(@PathVariable Long cursoId) {
        List<CalificacionDTO> calificaciones = calificacionService.getCalificacionesPorCurso(cursoId);
        return ResponseEntity.ok(ApiResponse.ok("Calificaciones del curso", calificaciones));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CalificacionDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody CalificacionDTO dto
    ) {
        CalificacionDTO updated = calificacionService.updateCalificacion(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Calificación actualizada", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        calificacionService.deleteCalificacion(id);
        return ResponseEntity.ok(ApiResponse.ok("Calificación eliminada", null));
    }
}

