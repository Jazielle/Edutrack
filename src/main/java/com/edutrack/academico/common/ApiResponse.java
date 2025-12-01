package com.edutrack.academico.common;

import java.time.LocalDateTime;

/**
 * ApiResponse<T>
 * -----------------------------------------------------
 * Estandariza las respuestas JSON del backend EduTrack.
 */
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp
) {

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data, LocalDateTime.now());
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null, LocalDateTime.now());
    }
}

