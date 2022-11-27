package com.github.marschall.stateofjpa310;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JPA_LOCAL_TIME")
public class JpaLocalTime {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "LOCAL_TIME")
  private LocalTime localTime;

  public JpaLocalTime() {
    super();
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalTime getLocalTime() {
    return this.localTime;
  }

  public void setLocalTime(LocalTime localTime) {
    this.localTime = localTime;
  }

}
