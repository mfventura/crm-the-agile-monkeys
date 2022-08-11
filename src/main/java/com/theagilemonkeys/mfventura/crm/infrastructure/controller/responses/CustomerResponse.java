package com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@Data
@Builder

public class CustomerResponse {
  private Integer id;
  private String name;
  private String surname;
  private String surname2;
  private String country;
  private String document;
}
