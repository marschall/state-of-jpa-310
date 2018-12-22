package com.github.marschall.stateofjpa310;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import com.github.marschall.stateofjpa310.configuration.MariaDdConfiguration;
import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;

class MariaDbOpenJpaStateOfJpa310Test extends AbstractStateOfJpa310Test {

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
    return "state-of-jpa-310-openjpa-mariadb";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return MariaDdConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
