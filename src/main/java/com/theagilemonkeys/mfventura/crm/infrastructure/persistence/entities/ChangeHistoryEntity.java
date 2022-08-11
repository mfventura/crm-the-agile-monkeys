package com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "change_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeHistoryEntity {
  @Embeddable
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Id implements Serializable {
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "date")
    private LocalDateTime date;
  }
  @EmbeddedId
  private Id id;
  private String description;
}
