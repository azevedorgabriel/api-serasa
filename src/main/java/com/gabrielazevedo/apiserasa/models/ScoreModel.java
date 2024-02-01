package com.gabrielazevedo.apiserasa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Entity
@Table(name = "SCORE")
@Getter
@Setter
public class ScoreModel extends RepresentationModel<ScoreModel> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String description;
    private Integer min;
    private Integer max;
}
