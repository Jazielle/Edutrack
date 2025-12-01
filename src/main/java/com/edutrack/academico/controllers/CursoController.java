package com.edutrack.academico.controllers;

import com.edutrack.academico.common.ApiResponse;
import com.edutrack.academico.dtos.curso.CursoDTO;
import com.edutrack.academico.services.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<ApiResponse<CursoDTO>> create(@Valid @RequestBody CursoDTO dto) {
        CursoDTO created = cursoService.createCurso(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Curso creado correctamente", created));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CursoDTO>>> getAll() {
        List<CursoDTO> cursos = cursoService.getCursos();
        return ResponseEntity.ok(ApiResponse.ok("Listado de cursos", cursos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CursoDTO>> getById(@PathVariable Long id) {
        CursoDTO curso = cursoService.getCurso(id);
        return ResponseEntity.ok(ApiResponse.ok("Detalle del curso", curso));
    }

    @GetMapping("/profesor/{profesorId}")
    public ResponseEntity<ApiResponse<List<CursoDTO>>> getByProfesor(@PathVariable Long profesorId) {
        List<CursoDTO> cursos = cursoService.getCursosPorProfesor(profesorId);
        return ResponseEntity.ok(ApiResponse.ok("Cursos del profesor", cursos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CursoDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody CursoDTO dto
    ) {
        CursoDTO updated = cursoService.updateCurso(id, dto);
        return ResponseEntity.ok(ApiResponse.ok("Curso actualizado", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        cursoService.deleteCurso(id);
        return ResponseEntity.ok(ApiResponse.ok("Curso eliminado", null));
    }
}

