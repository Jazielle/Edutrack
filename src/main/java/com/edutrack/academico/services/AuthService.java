package com.edutrack.academico.services;

import com.edutrack.academico.dtos.auth.AuthenticationRequest;
import com.edutrack.academico.dtos.auth.AuthenticationResponse;
import com.edutrack.academico.dtos.auth.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}

