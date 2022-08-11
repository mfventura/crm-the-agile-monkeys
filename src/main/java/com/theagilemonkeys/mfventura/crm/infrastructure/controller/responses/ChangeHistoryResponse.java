package com.theagilemonkeys.mfventura.crm.infrastructure.controller.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder

public class ChangeHistoryResponse {
  private Integer customerId;
  private Integer userId;
  private String description;
  private LocalDateTime date;
}
