package com.gabrielazevedo.apiserasa.repositories;

import com.gabrielazevedo.apiserasa.enums.PersonStatus;
import com.gabrielazevedo.apiserasa.models.PersonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<PersonModel, UUID> {
    Optional<PersonModel> findByIdAndStatus(UUID id, PersonStatus personStatus);
    List<PersonModel> findByNomeAndStatus(String nome, PersonStatus personStatus);
    List<PersonModel> findByIdadeAndStatus(Integer idade, PersonStatus personStatus);
    List<PersonModel> findByCepAndStatus(String cep, PersonStatus personStatus);
    Page<PersonModel> findAllByStatus(Pageable pageable, PersonStatus personStatus);
}
