package com.theagilemonkeys.mfventura.crm;

import com.theagilemonkeys.mfventura.crm.infrastructure.controller.CustomersController;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.CreateCustomerRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.UpdateCustomerRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses.CustomerResponse;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class CustomersEndpointTests {
  
  @Autowired
  private CustomersController customersController;
  
  @Test
  @Sql("/sql/insert-user.sql")
  @Sql("/sql/insert-customer.sql")
  public void getCustomerOk(){
    final var response = customersController.getCustomer(1);
    final var body = (CustomerResponse) response.getBody();
    assertEquals(response.getStatusCode().value(), (HttpStatus.OK.value()));
    assertEquals(body.getId(), 1);
  }
  
  @Test
  public void getCustomerNotFound(){
    final var response = customersController.getCustomer(1);
    assertEquals(response.getStatusCode().value(), (HttpStatus.NOT_FOUND.value()));
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  @Sql("/sql/insert-customer.sql")
  public void listCustomers(){
    final var response = customersController.listCustomers();
    final var body = (List<CustomerResponse>)response.getBody();
    assertEquals(response.getStatusCode().value(), (HttpStatus.OK.value()));
    assertEquals(body.size(), 1);
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  public void createCustomerOk() throws IOException {
    File imageFile = new File("src/test/resources/static/default-customer.jpeg");
    final var b64 = Base64.getEncoder().encodeToString(new FileInputStream(imageFile).readAllBytes());
    final var customer = CreateCustomerRequest.builder()
            .country("ES")
            .document("12345678Z")
            .name("Name")
            .surname("Surname")
            .surname2("Surname2")
            .image64(b64)
            .build();
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
  
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
    
    final var response = customersController.createCustomer(customer, auth);
    final var body = (CustomerResponse)response.getBody();
    assertEquals(body.getId(), 1);
    assertEquals(body.getHistory().size(), 1);
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  @Sql("/sql/insert-customer.sql")
  public void getCustomerImageOk() throws IOException {
    File imageFile = new File("src/test/resources/static/default-customer.jpeg");
    final var b64 = Base64.getEncoder().encodeToString(new FileInputStream(imageFile).readAllBytes());
    final var customer = UpdateCustomerRequest.builder()
            .country("UK")
            .document("12345678Z")
            .name("Name")
            .surname("Surname")
            .surname2("Surname2")
            .image64(b64)
            .build();
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
    
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
  
    customersController.updateCustomer(1, customer, auth);
    final var response = customersController.getCustomerImage(1);
    assertEquals(response.getHeaders().getContentType(), MediaType.IMAGE_JPEG);
  }
  
  @Test
  public void getCustomerImageNotFound(){
    final var response = customersController.getCustomerImage(1);
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  @Sql("/sql/insert-customer.sql")
  public void updateCustomerNoChangeImageOk(){
    final var customer = UpdateCustomerRequest.builder()
            .country("UK")
            .document("12345678Z")
            .name("Name")
            .surname("Surname")
            .surname2("Surname2")
            .image64("XXX")
            .build();
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
      
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
    
    final var response = customersController.updateCustomer(1, customer, auth);
    final var body = (CustomerResponse)response.getBody();
    assertEquals(body.getId(), 1);
    assertEquals(body.getCountry(), "UK");
    assertEquals(body.getHistory().size(), 2);
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  @Sql("/sql/insert-customer.sql")
  public void updateCustomerChangeImageOk() throws IOException {
    File imageFile = new File("src/test/resources/static/default-customer.jpeg");
    final var b64 = Base64.getEncoder().encodeToString(new FileInputStream(imageFile).readAllBytes());
    final var customer = UpdateCustomerRequest.builder()
            .country("UK")
            .document("12345678Z")
            .name("Name")
            .surname("Surname")
            .surname2("Surname2")
            .image64(b64)
            .build();
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
      
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
    
    final var response = customersController.updateCustomer(1, customer, auth);
    final var body = (CustomerResponse)response.getBody();
    assertEquals(body.getId(), 1);
    assertEquals(body.getCountry(), "UK");
    assertEquals(body.getHistory().size(), 2);
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  public void updateCustomerNotFound(){
    final var customer = UpdateCustomerRequest.builder()
            .country("UK")
            .document("12345678Z")
            .name("Name")
            .surname("Surname")
            .surname2("Surname2")
            .image64("XXX")
            .build();
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
      
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
    
    final var response = customersController.updateCustomer(1, customer, auth);
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
  @Test
  @Sql("/sql/insert-user.sql")
  @Sql("/sql/insert-customer.sql")
  public void deleteCustomerOk(){
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
    
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
    final var response = customersController.deleteCustomer(1, auth);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }
  @Test
  @Sql("/sql/insert-user.sql")
  public void deleteCustomerNotFound(){
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
    
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
    final var response = customersController.deleteCustomer(1, auth);
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
  @Test
  @Sql("/sql/insert-user.sql")
  @Sql("/sql/insert-customer.sql")
  public void recoverCustomerOk(){
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
    
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
    final var response = customersController.recoverCustomer(1, auth);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
  }
  @Test
  @Sql("/sql/insert-user.sql")
  public void recoverCustomerNotFound(){
    final var auth = new AbstractAuthenticationToken(null) {
      @Override
      public Object getCredentials() {
        return "";
      }
    
      @Override
      public Object getPrincipal() {
        return "1";
      }
    };
    final var response = customersController.recoverCustomer(1, auth);
    assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
  }
}
