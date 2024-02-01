package com.gabrielazevedo.apiserasa.models;

import com.gabrielazevedo.apiserasa.enums.PersonStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "PERSON")
@Getter
@Setter
public class PersonModel extends RepresentationModel<PersonModel> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;
    private Integer idade;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String telefone;
    private String scoreDescription;
    private Integer score;
    private PersonStatus status = PersonStatus.ENABLED;

    public boolean isActive() {
        return this.status == PersonStatus.ENABLED;
    }

    public void disable() {
        this.status = PersonStatus.DISABLED;
    }
}
