package com.github.marschall.stateofjpa310;

import java.time.temporal.TemporalUnit;

import com.github.marschall.stateofjpa310.configuration.FirebirdConfiguration;
import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;

class FirebirdOpenJpaTest extends AbstractStateOfJpa310Test {

  @Override
  protected int getDefaultNanoSecond() {
    return 123_400_000;
  }

  @Override
  protected TemporalUnit getTimestampResolution() {
    return HundredMicroseconds.INSTANCE;
  }

  @Override
  protected TemporalUnit getTimeResolution() {
    return HundredMicroseconds.INSTANCE;
  }

  @Override
  protected boolean offsetDateTimeSupported() {
    return false;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-openjpa-firebird";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return FirebirdConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
