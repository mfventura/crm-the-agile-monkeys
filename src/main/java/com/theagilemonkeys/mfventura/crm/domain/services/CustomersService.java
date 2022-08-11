package com.theagilemonkeys.mfventura.crm.domain.services;

import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.CreateCustomerRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses.CustomerResponse;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.CustomerEntity;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersService {
  @Autowired
  private CustomerRepository customerRepository;
  public List<CustomerResponse> listCustomers() {
    final var customers = customerRepository.findAll();
    return customers.stream()
            .map(c ->
                    mapCustomerResponse(c)
            ).toList();
  }
  
  public CustomerResponse createCustomer(CreateCustomerRequest customer, int userId) {
    final var entity = CustomerEntity.builder()
            .country(customer.getCountry())
            .document(customer.getDocument())
            .image64(customer.getImage64())
            .name(customer.getName())
            .surname(customer.getSurname())
            .surname2(customer.getSurname2())
            .build();
    customerRepository.save(entity);
    return mapCustomerResponse(entity);
  }
  
  public String getImageFromCustomer(Integer id){
    final var entity = customerRepository.findById(id);
    if(entity.isPresent()){
      return entity.get().getImage64();
    }
    return null;
  }
  private CustomerResponse mapCustomerResponse(CustomerEntity c) {
    return CustomerResponse.builder()
            .id(c.getId())
            .name(c.getName())
            .surname(c.getSurname())
            .surname2(c.getSurname2())
            .country(c.getCountry())
            .document(c.getDocument())
            .build();
  }
}
