package com.gabrielazevedo.apiserasa.dtos;

import com.gabrielazevedo.apiserasa.models.PersonModel;

public record PersonResponseDTO (String status, PersonModel person_data) {
}
