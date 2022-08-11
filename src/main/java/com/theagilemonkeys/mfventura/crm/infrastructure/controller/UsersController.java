package com.theagilemonkeys.mfventura.crm.infrastructure.controller;

import com.theagilemonkeys.mfventura.crm.domain.services.UsersService;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.CreateUserRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController extends ErrorHandler {
  @Autowired
  private UsersService usersService;
  
  @GetMapping()
  public ResponseEntity listUsers(){
    return ResponseEntity.ok(usersService.listUsers());
  }
  
  @PostMapping()
  public ResponseEntity createUser(@RequestBody CreateUserRequest request){
    final var user = usersService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }
  
  @GetMapping("/{id}")
  public ResponseEntity getUser(@PathVariable Integer id){
    final var user = usersService.getUser(id);
    if(user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
  
  @PutMapping("/{id}")
  public ResponseEntity updateUser(@PathVariable Integer id, UpdateUserRequest request) {
    final var user = usersService.updateUser(id, request);
    if(user != null) {
      return ResponseEntity.ok(user);
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
  }
  
  @DeleteMapping("/{id}")
  public ResponseEntity deleteUser(@PathVariable Integer id) {
    usersService.deleteUser(id);
    return ResponseEntity.ok().build();
  }
  
  @PutMapping("/{id}/recover")
  public ResponseEntity recoverUser(@PathVariable Integer id) {
    usersService.recoverUser(id);
    return ResponseEntity.ok().build();
  }
}
