package com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UpdateUserRequest {
  private boolean isAdmin;
  private String email;
}
