package com.github.marschall.stateofjpa310;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import com.github.marschall.stateofjpa310.configuration.Db2Configuration;
import com.github.marschall.stateofjpa310.configuration.HibernateConfiguration;

class Db2HibernateTest extends AbstractStateOfJpa310Test {

  @Override
  protected boolean offsetDateTimeSupported() {
    return false;
  }

  @Override
  protected TemporalUnit getTimeResolution() {
    return ChronoUnit.SECONDS;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-hibernate-db2";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return Db2Configuration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return HibernateConfiguration.class;
  }

}
