package com.github.marschall.stateofjpa310;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.junit.jupiter.api.Disabled;

import com.github.marschall.stateofjpa310.configuration.Db2Configuration;
import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;

@Disabled
class Db2OpenJpaTest extends AbstractStateOfJpa310Test {

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
    return "state-of-jpa-310-openjpa-db2";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return Db2Configuration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
