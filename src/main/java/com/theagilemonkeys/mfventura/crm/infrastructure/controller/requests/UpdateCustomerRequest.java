package com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests;

import com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.validation.CustomerValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@CustomerValidator.List(
        @CustomerValidator(field = "document", fieldMatch = "country", message = "error.document.pattern")
)
@AllArgsConstructor
@Data
@Builder
public class UpdateCustomerRequest {
  @NotEmpty(message = "error.name.nonempty")
  private String name;
  @NotEmpty(message = "error.surname.nonempty")
  private String surname;
  private String surname2;
  private String country;
  @NotEmpty(message = "error.document.nonempty")
  private String document;
  private String image64;
}
