package com.gabrielazevedo.apiserasa.dtos;

public record LoginResponseDTO(String status, String token, String expires_at) {
}
