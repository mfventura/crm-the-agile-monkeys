package com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories;

import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
  Optional<UserEntity> findByEmail(String email);
}
