package com.github.marschall.stateofjpa310;

import java.time.temporal.TemporalUnit;

import com.github.marschall.stateofjpa310.configuration.HibernateConfiguration;
import com.github.marschall.stateofjpa310.configuration.SqlServerConfiguration;

class SqlServerHibernateTest extends AbstractStateOfJpa310Test {

  @Override
  protected int getDefaultNanoSecond() {
    return 123_456_700;
  }

  @Override
  protected TemporalUnit getTimeResolution() {
    return HundredNanoseconds.INSTANCE;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-hibernate-sqlserver";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return SqlServerConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return HibernateConfiguration.class;
  }

}
