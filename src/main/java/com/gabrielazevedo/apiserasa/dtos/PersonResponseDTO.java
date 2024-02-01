package com.gabrielazevedo.apiserasa.dtos;

import java.util.UUID;

public record PersonResponseDTO (String status, String message, UUID id) {
}
