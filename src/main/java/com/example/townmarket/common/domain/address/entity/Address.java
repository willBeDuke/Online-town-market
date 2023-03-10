package com.example.townmarket.common.domain.address.entity;

import com.example.townmarket.common.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(nullable = false)
  private String address; // 시도
  @Column(nullable = false)
  private String address2; // 시군구
  @Column(nullable = false)
  private String address3; // 읍면동

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Address(String address, String address2, String address3, User user) {
    this.address = address;
    this.address2 = address2;
    this.address3 = address3;
    this.user = user;
  }


  public void updateAddress(String address, String address2, String address3) {
    this.address = address;
    this.address2 = address2;
    this.address3 = address3;
  }
}

