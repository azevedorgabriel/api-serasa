package com.gabrielazevedo.apiserasa.dtos;

import com.gabrielazevedo.apiserasa.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
