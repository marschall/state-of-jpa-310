package com.github.marschall.stateofjpa310;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.junit.jupiter.api.Disabled;

import com.github.marschall.stateofjpa310.configuration.MariaDdConfiguration;
import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;


@Disabled
class MariaDbOpenJpaTest extends AbstractStateOfJpa310Test {

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
