package com.theagilemonkeys.mfventura.crm.domain.services;

import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.CreateUserRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.UpdateUserRequest;
import com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses.UserResponse;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities.UserEntity;
import com.theagilemonkeys.mfventura.crm.infrastructure.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersService {
  @Autowired
  private UserRepository userRepository;
  public List<UserResponse> listUsers() {
    final var usersEntities = userRepository.findAll();
    return usersEntities.stream()
            .map(u ->
                    mapUserResponse(u)
            ).toList();
  }
  public UserResponse createUser(CreateUserRequest user){
    var userEntity = UserEntity.builder()
            .email(user.getEmail())
            .role(calculateRole(user.isAdmin()))
            .build();
    userRepository.save(userEntity);
    return mapUserResponse(userEntity);
  }
  public UserResponse getUser(Integer id){
    final var u = userRepository.findById(id);
    if(u.isPresent()){
      return mapUserResponse(u.get());
    }
    return null;
  }
  public UserResponse updateUser(Integer id, UpdateUserRequest user){
    var u = userRepository.findById(id);
    if(u.isPresent()){
      var userEntity = u.get();
      userEntity.setRole(calculateRole(user.isAdmin()));
      userEntity.setEmail(user.getEmail());
      userRepository.save(userEntity);
      return mapUserResponse(userEntity);
    }
    return null;
  }
  public void deleteUser(Integer id){
    var u = userRepository.findById(id);
    if(u.isPresent()){
      var userEntity = u.get();
      userEntity.setRemoveDate(LocalDateTime.now());
      userRepository.save(userEntity);
    }
  }
  public void recoverUser(Integer id){
    var u = userRepository.findById(id);
    if(u.isPresent()){
      var userEntity = u.get();
      userEntity.setRemoveDate(null);
      userRepository.save(userEntity);
    }
  }
  private UserResponse mapUserResponse(UserEntity u) {
    return UserResponse.builder()
            .id(u.getId())
            .email(u.getEmail())
            .isAdmin(isAdmin(u.getRole()))
            .build();
  }
  private boolean isAdmin(String role) {
    return role.equals("ADMIN");
  }
  private String calculateRole(boolean admin) {
    return admin ? "ADMIN" : "USER";
  }
}
