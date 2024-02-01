package com.gabrielazevedo.apiserasa.controllers;

import com.gabrielazevedo.apiserasa.dtos.PersonRecordDTO;
import com.gabrielazevedo.apiserasa.dtos.PersonResponseDTO;
import com.gabrielazevedo.apiserasa.models.PersonModel;
import com.gabrielazevedo.apiserasa.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping("/person")
    public ResponseEntity<PersonResponseDTO> savePerson(@RequestBody @Valid PersonRecordDTO personRecordDTO) {
        return personService.savePerson(personRecordDTO);
    }

    @GetMapping("/person")
    public ResponseEntity<Page<PersonModel>> getAllPerson(Pageable pageable) {
        return personService.getAllPerson(pageable);
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<Object> getOnePerson(@PathVariable(value = "id") UUID id) {
        return personService.getOnePerson(id);
    }

    @GetMapping("/person/search/nome/{nome}")
    public ResponseEntity<List<PersonModel>> getSearchPersonNome(@PathVariable(value = "nome") String nome) {
        return personService.getSearchPersonNome(nome);
    }

    @GetMapping("/person/search/idade/{idade}")
    public ResponseEntity<List<PersonModel>> getSearchPersonIdade(@PathVariable(value = "idade") String idade) {
        return personService.getSearchPersonIdade(idade);
    }

    @GetMapping("/person/search/cep/{cep}")
    public ResponseEntity<List<PersonModel>> getSearchPersonCep(@PathVariable(value = "cep") String cep) {
        return personService.getSearchPersonCep(cep);
    }

    @PutMapping("/person/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid PersonRecordDTO personRecordDTO) {
        return personService.updatePerson(id, personRecordDTO);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<Object> deletePerson(@PathVariable(value = "id") UUID id) {
        return personService.deletePerson(id);
    }

}
