package com.gabrielazevedo.apiserasa.services;

import com.gabrielazevedo.apiserasa.controllers.PersonController;
import com.gabrielazevedo.apiserasa.dtos.PersonRecordDTO;
import com.gabrielazevedo.apiserasa.dtos.PersonResponseDTO;
import com.gabrielazevedo.apiserasa.enums.PersonStatus;
import com.gabrielazevedo.apiserasa.models.PersonModel;
import com.gabrielazevedo.apiserasa.repositories.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {
    private static final String PERSON_CREATED = "Person created successfully!";
    private static final String PERSON_NOT_FOUND = "Person not found.";
    private static final String PERSON_UPDATED = "Person updated successfully!";
    private static final String PERSON_DELETED = "Person deleted succesfully.";

    @Autowired
    PersonRepository personRepository;
    @Autowired
    ScoreService scoreService;

    @Value("${api.response.error}")
    private String response_error;
    @Value("${api.response.success}")
    private String response_success;
    @Value("${api.page.default}")
    private int pageDefault;
    @Value("${api.page.size}")
    private int sizeDefault;

    private ResponseEntity<Object> personNotFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new PersonResponseDTO(response_error, PERSON_NOT_FOUND, null));
    }

    private ResponseEntity<List<PersonModel>> personResponse(List<PersonModel> personModelList) {

        if (!personModelList.isEmpty()) {
            for (PersonModel person : personModelList) {
                person.add(linkTo(methodOn(PersonController.class).getOnePerson(person.getId())).withSelfRel());
                person.setScoreDescription(scoreService.getScoreDescription(person.getScore()));
            }
        }

        return ResponseEntity.ok().body(personModelList);
    }

    public ResponseEntity<PersonResponseDTO> savePerson(PersonRecordDTO personRecordDTO) {
        var personModel = new PersonModel();
        BeanUtils.copyProperties(personRecordDTO, personModel);
        personRepository.save(personModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PersonResponseDTO(response_success, PERSON_CREATED, personModel.getId()));
    }

    public ResponseEntity<Object> getOnePerson(UUID id) {
        Optional<PersonModel> person = personRepository.findByIdAndStatus(id, PersonStatus.ENABLED);

        if (person.isEmpty()) {
            return personNotFound();
        }

        var personModel = person.get();
        Pageable pageable = PageRequest.of(pageDefault, sizeDefault);
        personModel.add(linkTo(methodOn(PersonController.class).getAllPerson(pageable)).withRel("Person List"));
        personModel.setScoreDescription(scoreService.getScoreDescription(personModel.getScore()));
        return ResponseEntity.ok().body(personModel);
    }

    public ResponseEntity<Page<PersonModel>> getAllPerson(Pageable pageable) {
        Page<PersonModel> personModelList = personRepository.findAllByStatus(pageable, PersonStatus.ENABLED);

        if (!personModelList.isEmpty()) {
            for (PersonModel person : personModelList) {
                person.add(linkTo(methodOn(PersonController.class).getOnePerson(person.getId())).withSelfRel());
                person.setScoreDescription(scoreService.getScoreDescription(person.getScore()));
            }
        }
        return ResponseEntity.ok(personModelList);
    }

    public ResponseEntity<List<PersonModel>> getSearchPersonNome(String nome) {
        List<PersonModel> personModelList = personRepository.findByNomeAndStatus(nome, PersonStatus.ENABLED);
        return personResponse(personModelList);
    }

    public ResponseEntity<List<PersonModel>> getSearchPersonIdade(String idade) {
        List<PersonModel> personModelList = personRepository.findByIdadeAndStatus(Integer.valueOf(idade), PersonStatus.ENABLED);
        return personResponse(personModelList);
    }

    public ResponseEntity<List<PersonModel>> getSearchPersonCep(String cep) {
        List<PersonModel> personModelList = personRepository.findByCepAndStatus(cep, PersonStatus.ENABLED);
        return personResponse(personModelList);
    }

    public ResponseEntity<Object> updatePerson(UUID id, PersonRecordDTO personRecordDTO) {
        Optional<PersonModel> person = personRepository.findByIdAndStatus(id, PersonStatus.ENABLED);

        if (person.isEmpty()) {
            return personNotFound();
        }

        var personModel = person.get();
        BeanUtils.copyProperties(personRecordDTO, personModel);
        personRepository.save(personModel);
        return ResponseEntity.ok().body(new PersonResponseDTO(response_success, PERSON_UPDATED, personModel.getId()));
    }

    public ResponseEntity<Object> deletePerson(UUID id) {
        Optional<PersonModel> person = personRepository.findByIdAndStatus(id, PersonStatus.ENABLED);

        if (person.isEmpty()) {
            return personNotFound();
        }

        var personModel = person.get();
        personModel.disable();
        personRepository.save(personModel);
        return ResponseEntity.ok().body(new PersonResponseDTO(response_success, PERSON_DELETED, null));
    }
}
