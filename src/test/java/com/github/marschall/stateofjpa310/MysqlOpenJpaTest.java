package com.github.marschall.stateofjpa310;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import com.github.marschall.stateofjpa310.configuration.MysqlConfiguration;
import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;

class MysqlOpenJpaTest extends AbstractStateOfJpa310Test {

  @Override
  protected TemporalUnit getTimestampResolution() {
    return ChronoUnit.MICROS;
  }

  @Override
  protected TemporalUnit getTimeResolution() {
    return ChronoUnit.MICROS;
  }

  @Override
  protected boolean offsetDateTimeSupported() {
    return false;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-openjpa-mysql";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return MysqlConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
