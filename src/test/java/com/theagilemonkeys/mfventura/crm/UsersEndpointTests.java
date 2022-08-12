package com.theagilemonkeys.mfventura.crm;

import com.theagilemonkeys.mfventura.crm.infrastructure.controller.UsersController;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.CreateUserRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.UpdateUserRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class UsersEndpointTests {
  
  @Autowired
  private UsersController usersController;
  
  @Test
  @Sql("/sql/insert-user.sql")
  public void getUserOk(){
    final var response = usersController.getUser(1);
    final var body = (UserResponse) response.getBody();
    assertEquals(response.getStatusCode().value(), (HttpStatus.OK.value()));
    assertEquals(body.getId(), 1);
  }
  
  @Test
  public void getUserNotFound(){
    final var response = usersController.getUser(2);
    assertEquals(response.getStatusCode().value(), (HttpStatus.NOT_FOUND.value()));
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  public void listUserOk(){
    final var response = usersController.listUsers();
    assertEquals(response.getStatusCode().value(), (HttpStatus.OK.value()));
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  public void updateUserOk(){
    final var request = UpdateUserRequest.builder()
            .email("newMail@mail.es")
            .isAdmin(true)
            .build();
    final var response = usersController.updateUser(1, request);
    final var body = (UserResponse) response.getBody();
    assertEquals(response.getStatusCode().value(), (HttpStatus.OK.value()));
    assertEquals(body.getEmail(), "newMail@mail.es");
    assertEquals(body.isAdmin(), true);
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  public void updateUserNotFound(){
    final var request = UpdateUserRequest.builder()
            .email("newMail@mail.es")
            .isAdmin(false)
            .build();
    final var response = usersController.updateUser(2, request);
    final var body = (UserResponse) response.getBody();
    assertEquals(response.getStatusCode().value(), (HttpStatus.NOT_FOUND.value()));
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  public void deleteUserOk(){
    final var response = usersController.deleteUser(1);
    assertEquals(response.getStatusCode().value(), (HttpStatus.OK.value()));
  }
  
  @Test
  public void deleteUserNotExists(){
    final var response = usersController.deleteUser(1);
    assertEquals(response.getStatusCode().value(), (HttpStatus.NOT_FOUND.value()));
  }
  
  @Test
  @Sql("/sql/insert-user.sql")
  public void recoverUserOk(){
    final var response = usersController.recoverUser(1);
    assertEquals(response.getStatusCode().value(), (HttpStatus.OK.value()));
  }
  
  @Test
  public void recoverUserNotFound(){
    final var response = usersController.recoverUser(1);
    assertEquals(response.getStatusCode().value(), (HttpStatus.NOT_FOUND.value()));
  }
  
  @Test
  public void createUserOk(){
    final var request = CreateUserRequest.builder()
            .email("mymail@mail.es")
            .isAdmin(false)
            .build();
    final var response = usersController.createUser(request);
    final var body = (UserResponse) response.getBody();
    assertEquals(response.getStatusCode().value(), (HttpStatus.CREATED.value()));
    assertEquals(body.getId(), 1);
  }
  
}
