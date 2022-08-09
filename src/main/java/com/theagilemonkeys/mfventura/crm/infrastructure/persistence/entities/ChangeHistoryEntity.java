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
  }
  @EmbeddedId
  private Id id;
  private String description;
  private LocalDateTime date;
  @ManyToOne
  @JoinColumn(name = "user_id",referencedColumnName = "id", insertable = false, updatable = false)
  private UserEntity user;
  @ManyToOne
  @JoinColumn(name = "customer_id",referencedColumnName = "id", insertable = false, updatable = false)
  private CustomerEntity customer;
}
