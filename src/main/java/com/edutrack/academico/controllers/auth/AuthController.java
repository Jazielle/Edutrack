package com.edutrack.academico.controllers.auth;

import com.edutrack.academico.dtos.auth.AuthenticationRequest;
import com.edutrack.academico.dtos.auth.AuthenticationResponse;
import com.edutrack.academico.dtos.auth.RegisterRequest;
import com.edutrack.academico.entities.User;
import com.edutrack.academico.repositories.UserRepository;
import com.edutrack.academico.serviceimpls.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthServiceImpl authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        return userRepository.findByEmail(authentication.getName())
                .<ResponseEntity<?>>map(user -> {
                    User safeUser = User.builder()
                            .id(user.getId())
                            .firstname(user.getFirstname())
                            .lastname(user.getLastname())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .build();
                    return ResponseEntity.ok().body(safeUser);
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Usuario no encontrado"));
    }
}

