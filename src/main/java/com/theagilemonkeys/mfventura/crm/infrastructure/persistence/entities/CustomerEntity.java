package com.theagilemonkeys.mfventura.crm.infrastructure.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private String surname;
  @Column(name = "surname_2")
  private String surname2;
  private String country;
  @Column(unique = true)
  private String document;
  @Column(length = 1000000000)
  private String image64;
  @Column(name = "remove_date")
  private LocalDateTime removeDate;
  @OneToMany
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  private List<ChangeHistoryEntity> history;
}
