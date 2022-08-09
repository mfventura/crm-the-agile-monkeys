package com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories;

import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.CustomerEntity;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
  
}