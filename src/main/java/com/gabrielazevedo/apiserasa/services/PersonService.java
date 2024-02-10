package com.gabrielazevedo.apiserasa.services;

import com.gabrielazevedo.apiserasa.controllers.PersonController;
import com.gabrielazevedo.apiserasa.dtos.PersonRecordDTO;
import com.gabrielazevedo.apiserasa.dtos.PersonResponseDTO;
import com.gabrielazevedo.apiserasa.dtos.ResponseDefaultDTO;
import com.gabrielazevedo.apiserasa.enums.PersonStatus;
import com.gabrielazevedo.apiserasa.exceptions.PersonNotFoundException;
import com.gabrielazevedo.apiserasa.models.CepModel;
import com.gabrielazevedo.apiserasa.models.PersonModel;
import com.gabrielazevedo.apiserasa.repositories.PersonRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {
    private static final String PERSON_CREATED = "Person created successfully!";
    private static final String PERSON_UPDATED = "Person updated successfully!";
    private static final String PERSON_DELETED = "Person deleted succesfully.";

    @Autowired
    PersonRepository personRepository;
    @Autowired
    ScoreService scoreService;
    @Autowired
    CepService cepService;

    @Value("${api.response.success}")
    private String response_success;
    @Value("${api.page.default}")
    private int pageDefault;
    @Value("${api.page.size}")
    private int sizeDefault;

    private List<PersonModel> addLinkPersonResponse(List<PersonModel> personModelList) {
        if (!personModelList.isEmpty()) {
            for (PersonModel person : personModelList) {
                person.add(linkTo(methodOn(PersonController.class).getOnePerson(person.getId())).withSelfRel());
                person.setScoreDescription(scoreService.getScoreDescription(person.getScore()));
            }
        }

        return personModelList;
    }

    private void saveUpdatePerson(PersonModel personModel, PersonRecordDTO personRecordDTO) {
        var oldCep = personModel.getCep();
        BeanUtils.copyProperties(personRecordDTO, personModel);

        //in case of a new person, oldCep will be null, so app will get additional data cep
        //in case of an update, app checks if entered cep(zipcode) is different from the one stored previously
        if (oldCep == null || !Objects.equals(personRecordDTO.cep(), oldCep)) {
            try {
                CepModel cep = cepService.getAdditionalDataCep(personModel.getCep());
                personModel.setEstado(cep.getUf());
                personModel.setCidade(cep.getLocalidade());
                personModel.setBairro(cep.getBairro());
                personModel.setLogradouro(cep.getLogradouro());
            } catch (Exception ignored) { }
        }

        personRepository.save(personModel);
    }

    public ResponseDefaultDTO savePerson(PersonRecordDTO personRecordDTO) {
        this.saveUpdatePerson(new PersonModel(), personRecordDTO);
        return new ResponseDefaultDTO(response_success, PERSON_CREATED);
    }

    public Page<PersonModel> getAllPerson(Pageable pageable) {
        Page<PersonModel> personModelList = personRepository.findAllByStatus(pageable, PersonStatus.ENABLED);

        if (!personModelList.isEmpty()) {
            for (PersonModel person : personModelList) {
                person.add(linkTo(methodOn(PersonController.class).getOnePerson(person.getId())).withSelfRel());
                person.setScoreDescription(scoreService.getScoreDescription(person.getScore()));
            }
        }
        return personModelList;
    }

    public PersonResponseDTO getOnePerson(UUID id) {
        Optional<PersonModel> person = personRepository.findByIdAndStatus(id, PersonStatus.ENABLED);

        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        }

        var personModel = person.get();
        Pageable pageable = PageRequest.of(pageDefault, sizeDefault);
        personModel.add(linkTo(methodOn(PersonController.class).getAllPerson(pageable)).withRel("Person List"));
        personModel.setScoreDescription(scoreService.getScoreDescription(personModel.getScore()));
        return new PersonResponseDTO(response_success, personModel);
    }

    public List<PersonModel> getSearchPersonNome(String nome) {
        return addLinkPersonResponse(personRepository.findByNomeAndStatus(nome, PersonStatus.ENABLED));
    }

    public List<PersonModel> getSearchPersonIdade(String idade) {
        return addLinkPersonResponse(personRepository.findByIdadeAndStatus(Integer.valueOf(idade), PersonStatus.ENABLED));
    }

    public List<PersonModel> getSearchPersonCep(String cep) {
        return addLinkPersonResponse(personRepository.findByCepAndStatus(cep, PersonStatus.ENABLED));
    }

    public ResponseDefaultDTO updatePerson(UUID id, PersonRecordDTO personRecordDTO) {
        Optional<PersonModel> person = personRepository.findByIdAndStatus(id, PersonStatus.ENABLED);

        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        }

        this.saveUpdatePerson(person.get(), personRecordDTO);
        return new ResponseDefaultDTO(response_success, PERSON_UPDATED);
    }

    public ResponseDefaultDTO deletePerson(UUID id) {
        Optional<PersonModel> person = personRepository.findByIdAndStatus(id, PersonStatus.ENABLED);

        if (person.isEmpty()) {
            throw new PersonNotFoundException();
        }

        var personModel = person.get();
        personModel.disable();
        personRepository.save(personModel);
        return new ResponseDefaultDTO(response_success, PERSON_DELETED);
    }
}
