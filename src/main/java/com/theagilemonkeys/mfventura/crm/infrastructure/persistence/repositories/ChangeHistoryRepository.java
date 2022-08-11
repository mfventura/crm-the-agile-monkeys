package com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories;

import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.ChangeHistoryEntity;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeHistoryRepository extends JpaRepository<ChangeHistoryEntity, ChangeHistoryEntity.Id> {
  
}