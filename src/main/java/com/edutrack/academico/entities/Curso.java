package com.edutrack.academico.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Ahora solo se guarda el nombre del profesor como texto
    @Column(name = "profesor", nullable = false, length = 120)
    private String profesor;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscripcion> inscripciones = new ArrayList<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calificacion> calificaciones = new ArrayList<>();
}

