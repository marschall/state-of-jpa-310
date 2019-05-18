package com.github.marschall.stateofjpa310;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
