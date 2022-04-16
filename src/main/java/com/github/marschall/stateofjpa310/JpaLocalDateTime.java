package com.github.marschall.stateofjpa310;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "JPA_LOCAL_DATE_TIME")
public class JpaLocalDateTime {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "LOCAL_DATE_TIME")
  private LocalDateTime localDateTime;

  public JpaLocalDateTime() {
    super();
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDateTime getLocalDateTime() {
    return this.localDateTime;
  }

  public void setLocalDateTime(LocalDateTime localDateTime) {
    this.localDateTime = localDateTime;
  }

}
