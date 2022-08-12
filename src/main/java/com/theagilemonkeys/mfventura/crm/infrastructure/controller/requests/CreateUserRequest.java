package com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Data
@Builder
public class CreateUserRequest {
  @NotEmpty(message = "error.email.nonempty")
  @Email(message = "error.email.pattern")
  private String email;
  private boolean isAdmin;
}
