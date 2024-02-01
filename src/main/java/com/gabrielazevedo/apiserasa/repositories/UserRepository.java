package com.gabrielazevedo.apiserasa.repositories;

import com.gabrielazevedo.apiserasa.models.PersonModel;
import com.gabrielazevedo.apiserasa.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    UserDetails findByLogin(String login);
}
