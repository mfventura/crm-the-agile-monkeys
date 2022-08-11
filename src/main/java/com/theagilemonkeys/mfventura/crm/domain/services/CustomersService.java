package com.theagilemonkeys.mfventura.crm.domain.services;

import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.CreateCustomerRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.UpdateCustomerRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses.ChangeHistoryResponse;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses.CustomerResponse;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses.UserResponse;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.ChangeHistoryEntity;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.CustomerEntity;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories.ChangeHistoryRepository;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomersService {
  @Autowired
  private CustomerRepository customerRepository;
  @Autowired
  private ChangeHistoryRepository changeHistoryRepository;
  
  public List<CustomerResponse> listCustomers() {
    final var customers = customerRepository.findAll();
    return customers.stream()
            .map(c ->
                    mapCustomerResponse(c)
            ).toList();
  }
  
  public CustomerResponse createCustomer(CreateCustomerRequest customer, int userId) {
    var entity = CustomerEntity.builder()
            .country(customer.getCountry())
            .document(customer.getDocument())
            .image64(customer.getImage64()) //TODO default
            .name(customer.getName())
            .surname(customer.getSurname())
            .surname2(customer.getSurname2())
            .history(new ArrayList<>())
            .build();
    customerRepository.save(entity);
  
    var changeHistoryEntity = createChangeHistory(entity.getId(), userId, "created");
    changeHistoryRepository.save(changeHistoryEntity);
    return mapCustomerResponse(entity);
  }
  public CustomerResponse getCustomer(Integer id){
    final var entity = customerRepository.findById(id);
    if(entity.isPresent()){
      return mapCustomerResponse(entity.get());
    }
    return null;
  }
  public String getImageFromCustomer(Integer id){
    final var entity = customerRepository.findById(id);
    if(entity.isPresent()){
      return entity.get().getImage64();
    }
    return null;
  }
  public CustomerResponse updateCustomer(Integer id, int userId, UpdateCustomerRequest request) {
    final var entity = customerRepository.findById(id);
    if(entity.isPresent()){
      var customer = entity.get();
      customer.setCountry(request.getCountry());
      customer.setDocument(request.getDocument());
      customer.setName(request.getName());
      customer.setSurname(request.getSurname());
      customer.setSurname2(request.getSurname2());
      if(!"XXX".equals(request.getImage64())){
        customer.setImage64(request.getImage64());
      }
      customerRepository.save(customer);
      var changeHistoryEntity = createChangeHistory(customer.getId(), userId, "updated");
      changeHistoryRepository.save(changeHistoryEntity);
      return mapCustomerResponse(customer);
    }
    return null;
  }
  private ChangeHistoryEntity createChangeHistory(Integer id, int userId, String description) {
    return ChangeHistoryEntity.builder()
            .id(ChangeHistoryEntity.Id.builder()
                    .customerId(id)
                    .userId(userId)
                    .date(LocalDateTime.now())
                    .build())
            .description(description)
            .build();
  }
  private CustomerResponse mapCustomerResponse(CustomerEntity c) {
    final var history = c.getHistory().stream().map(h -> mapChangeHistoryResponse(h)).toList();
    return CustomerResponse.builder()
            .id(c.getId())
            .name(c.getName())
            .surname(c.getSurname())
            .surname2(c.getSurname2())
            .country(c.getCountry())
            .document(c.getDocument())
            .imageURL("/api/v1/customers/"+c.getId()+"/image")
            .image64("XXX") //read API doc: Switch to comunicate changes, if image64 != XXX -> Some change on update
            .history(history)
            .build();
  }
  private ChangeHistoryResponse mapChangeHistoryResponse(ChangeHistoryEntity h) {
    return ChangeHistoryResponse.builder()
            .customerId(h.getId().getCustomerId())
            .userId(h.getId().getUserId())
            .date(h.getId().getDate())
            .description(h.getDescription())
            .build();
  }
  public void deleteCustomer(Integer id, int userId) {
    var entity = customerRepository.findById(id);
    if(entity.isPresent()){
      var c = entity.get();
      c.setRemoveDate(LocalDateTime.now());
      customerRepository.save(c);
      var changeHistoryEntity = createChangeHistory(c.getId(), userId, "deletion");
      changeHistoryRepository.save(changeHistoryEntity);
    }
  }
  public void recoverCustomer(Integer id, int userId) {
    var entity = customerRepository.findById(id);
    if(entity.isPresent()){
      var c = entity.get();
      c.setRemoveDate(null);
      customerRepository.save(c);
      var changeHistoryEntity = createChangeHistory(c.getId(), userId, "restore");
      changeHistoryRepository.save(changeHistoryEntity);
    }
  }
}
