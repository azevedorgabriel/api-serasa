package com.gabrielazevedo.apiserasa.dtos;


import com.gabrielazevedo.apiserasa.models.PersonModel;
import jakarta.validation.constraints.*;
import org.springframework.data.domain.Page;

public record PersonRecordDTO(
        @NotBlank
        String nome,

        @NotNull
        Integer idade,

        @NotBlank
        String cep,

        @NotBlank
        String estado,

        @NotBlank
        String cidade,

        @NotBlank
        String bairro,

        @NotBlank
        String logradouro,

        @NotNull
        String telefone,

        @NotNull
        @Min(value = 0, message = "Score should not be less than 0")
        @Max(value = 1000, message = "Score should not be greater than 1000")
        Integer score) {
        //getters and setter auto
}
