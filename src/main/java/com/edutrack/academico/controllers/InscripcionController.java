package com.edutrack.academico.controllers;

import com.edutrack.academico.common.ApiResponse;
import com.edutrack.academico.dtos.inscripcion.InscripcionDTO;
import com.edutrack.academico.services.InscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
@CrossOrigin("*")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<ApiResponse<InscripcionDTO>> create(@Valid @RequestBody InscripcionDTO dto) {
        InscripcionDTO created = inscripcionService.createInscripcion(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Inscripción registrada", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InscripcionDTO>>> getAll() {
        List<InscripcionDTO> inscripciones = inscripcionService.getInscripciones();
        return ResponseEntity.ok(ApiResponse.ok("Listado de inscripciones", inscripciones));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InscripcionDTO>> getById(@PathVariable Long id) {
        InscripcionDTO inscripcion = inscripcionService.getInscripcion(id);
        return ResponseEntity.ok(ApiResponse.ok("Detalle de la inscripción", inscripcion));
    }

    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<ApiResponse<List<InscripcionDTO>>> getByEstudiante(@PathVariable Long estudianteId) {
        List<InscripcionDTO> inscripciones = inscripcionService.getInscripcionesPorEstudiante(estudianteId);
        return ResponseEntity.ok(ApiResponse.ok("Inscripciones del estudiante", inscripciones));
    }

    @GetMapping("/curso/{cursoId}")
    public ResponseEntity<ApiResponse<List<InscripcionDTO>>> getByCurso(@PathVariable Long cursoId) {
        List<InscripcionDTO> inscripciones = inscripcionService.getInscripcionesPorCurso(cursoId);
        return ResponseEntity.ok(ApiResponse.ok("Inscripciones del curso", inscripciones));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        inscripcionService.deleteInscripcion(id);
        return ResponseEntity.ok(ApiResponse.ok("Inscripción eliminada", null));
    }
}

