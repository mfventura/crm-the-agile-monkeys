package com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class UpdateCustomerRequest {
  private String name;
  private String surname;
  private String surname2;
  private String country;
  private String document;
  private String image64;
}
