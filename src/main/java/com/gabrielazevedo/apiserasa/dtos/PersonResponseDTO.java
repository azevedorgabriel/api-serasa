package com.gabrielazevedo.apiserasa.dtos;

import com.gabrielazevedo.apiserasa.models.PersonModel;

import java.util.UUID;

public record PersonResponseDTO (String status, PersonModel person_data) {
}
