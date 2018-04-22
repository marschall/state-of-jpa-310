package com.github.marschall.stateofjpa310;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JPA_LOCAL_DATE_TIME")
public class JpaLocalDateTime implements Serializable {

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
