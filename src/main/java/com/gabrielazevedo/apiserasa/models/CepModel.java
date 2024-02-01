package com.gabrielazevedo.apiserasa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@AllArgsConstructor
public class CepModel {
    private String cep;
    private String uf;
    private String localidade;
    private String bairro;
    private String logradouro;
}
