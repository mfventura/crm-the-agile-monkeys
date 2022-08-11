package com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UserResponse {
  private Integer id;
  private String email;
  private boolean isAdmin;
}
