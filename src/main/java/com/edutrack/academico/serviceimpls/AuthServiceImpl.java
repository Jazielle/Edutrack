package com.edutrack.academico.serviceimpls;

import com.edutrack.academico.dtos.auth.AuthenticationRequest;
import com.edutrack.academico.dtos.auth.AuthenticationResponse;
import com.edutrack.academico.dtos.auth.RegisterRequest;
import com.edutrack.academico.entities.Role;
import com.edutrack.academico.entities.User;
import com.edutrack.academico.repositories.UserRepository;
import com.edutrack.academico.security.JwtService;
import com.edutrack.academico.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        Role role = parseRole(request.getRole());

        boolean emailEnUso = userRepository.existsByEmail(request.getEmail());
        boolean nombreApellidoEnUso = userRepository
                .existsByFirstnameIgnoreCaseAndLastnameIgnoreCase(request.getFirstname(), request.getLastname());

        if (emailEnUso || nombreApellidoEnUso) {
            StringBuilder mensaje = new StringBuilder("No se puede registrar el usuario: ");
            if (emailEnUso) {
                mensaje.append("el correo ya está en uso");
            }
            if (nombreApellidoEnUso) {
                if (emailEnUso) {
                    mensaje.append(" y ");
                }
                mensaje.append("ya existe un usuario con el mismo nombre y apellido");
            }
            mensaje.append(".");
            throw new IllegalArgumentException(mensaje.toString());
        }

        User user = User.builder()
                .firstname(resolveValue(request.getFirstname(), "Usuario"))
                .lastname(resolveValue(request.getLastname(), "Academico"))
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        String token = jwtService.generateToken(user);

        return buildResponse(user, token);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String token = jwtService.generateToken(user);
        return buildResponse(user, token);
    }

    private AuthenticationResponse buildResponse(User user, String token) {
        return AuthenticationResponse.builder()
                .token(token)
                .userId(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole().name())
                .build();
    }

    private Role parseRole(String value) {
        if (value == null || value.isBlank()) {
            return Role.ESTUDIANTE;
        }

        try {
            return Role.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return Role.ESTUDIANTE;
        }
    }

    private String resolveValue(String value, String fallback) {
        return (value == null || value.isBlank()) ? fallback : value;
    }
}

