package com.github.marschall.stateofjpa310;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JPA_OFFSET_DATE_TIME")
public class JpaOffsetDateTime {

  @Id
  @Column(name = "ID")
  private Integer id;

  @Column(name = "OFFSET_DATE_TIME")
  private OffsetDateTime offsetDateTime;

  public JpaOffsetDateTime() {
    super();
  }

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public OffsetDateTime getOffsetDateTime() {
    return this.offsetDateTime;
  }

  public void setOffsetDateTime(OffsetDateTime offsetDateTime) {
    this.offsetDateTime = offsetDateTime;
  }

}
