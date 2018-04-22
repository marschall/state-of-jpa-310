package com.github.marschall.stateofjpa310;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JPA_SUPPORT")
public class JpaSupport implements Serializable {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "LOCAL_DATE_TIME")
  private LocalDateTime localDateTime;

  @Column(name = "OFFSET_DATE_TIME")
  private OffsetDateTime offsetDateTime;

  @Column(name = "LOCAL_TIME")
  private LocalTime localTime;

  public JpaSupport() {
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

  public OffsetDateTime getOffsetDateTime() {
    return this.offsetDateTime;
  }

  public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
    this.offsetDateTime = offsetDateTime;
  }

  public LocalTime getLocalTime() {
    return this.localTime;
  }

  public void setLocalTime(LocalTime localTime) {
    this.localTime = localTime;
  }

}
