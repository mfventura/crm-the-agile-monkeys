package com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

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
  private String imageURL;
  private String image64;
  private List<ChangeHistoryResponse> history;
}
