package com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CreateUserRequest {
  private String email;
  private boolean isAdmin;
}
