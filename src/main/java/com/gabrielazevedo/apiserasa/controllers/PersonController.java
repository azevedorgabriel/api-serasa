package com.gabrielazevedo.apiserasa.controllers;

import com.gabrielazevedo.apiserasa.dtos.PersonRecordDTO;
import com.gabrielazevedo.apiserasa.dtos.PersonResponseDTO;
import com.gabrielazevedo.apiserasa.models.PersonModel;
import com.gabrielazevedo.apiserasa.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/person", produces = {"application/json"})
@Tag(name = "api-serasa/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realiza o Cadastro de Pessoas", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created successfully!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User must be ADMIN")
    })
    public ResponseEntity<PersonResponseDTO> savePerson(@RequestBody @Valid PersonRecordDTO personRecordDTO) {
        return personService.savePerson(personRecordDTO);
    }

    @GetMapping
    @Operation(summary = "Realiza a Consulta de Pessoas", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person listed successfully!")
    })
    public ResponseEntity<Page<PersonModel>> getAllPerson(Pageable pageable) {
        return personService.getAllPerson(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Realiza a Consulta de Pessoa por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person listed successfully!"),
            @ApiResponse(responseCode = "400", description = "Person not found!")
    })
    public ResponseEntity<PersonResponseDTO> getOnePerson(@PathVariable(value = "id") UUID id) {
        return personService.getOnePerson(id);
    }

    @GetMapping("/search/nome/{nome}")
    @Operation(summary = "Realiza a Consulta de Pessoa por Nome", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person listed successfully!"),
            @ApiResponse(responseCode = "400", description = "Person not found!")
    })
    public ResponseEntity<List<PersonModel>> getSearchPersonNome(@PathVariable(value = "nome") String nome) {
        return personService.getSearchPersonNome(nome);
    }

    @GetMapping("/search/idade/{idade}")

    @Operation(summary = "Realiza a Consulta de Pessoa por Idade", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person listed successfully!"),
            @ApiResponse(responseCode = "400", description = "Person not found!")
    })
    public ResponseEntity<List<PersonModel>> getSearchPersonIdade(@PathVariable(value = "idade") String idade) {
        return personService.getSearchPersonIdade(idade);
    }

    @GetMapping("/search/cep/{cep}")

    @Operation(summary = "Realiza a Consulta de Pessoa por CEP", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person listed successfully!"),
            @ApiResponse(responseCode = "400", description = "Person not found!")
    })
    public ResponseEntity<List<PersonModel>> getSearchPersonCep(@PathVariable(value = "cep") String cep) {
        return personService.getSearchPersonCep(cep);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Realiza a Atualização dos dados de Pessoa por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated successfully!"),
            @ApiResponse(responseCode = "400", description = "Person not found!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User must be ADMIN")
    })
    public ResponseEntity<PersonResponseDTO> updatePerson(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid PersonRecordDTO personRecordDTO) {
        return personService.updatePerson(id, personRecordDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Realiza a Exclusão Lógica de Pessoa por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person deleted successfully!"),
            @ApiResponse(responseCode = "400", description = "Person not found!"),
            @ApiResponse(responseCode = "403", description = "Forbidden - User must be ADMIN")
    })
    public ResponseEntity<PersonResponseDTO> deletePerson(@PathVariable(value = "id") UUID id) {
        return personService.deletePerson(id);
    }

}
