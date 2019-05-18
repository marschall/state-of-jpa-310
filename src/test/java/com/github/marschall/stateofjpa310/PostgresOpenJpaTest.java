package com.github.marschall.stateofjpa310;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;
import com.github.marschall.stateofjpa310.configuration.PostgresConfiguration;

class PostgresOpenJpaTest extends AbstractStateOfJpa310Test {

  @Override
  protected TemporalUnit getTimeResolution() {
    return ChronoUnit.MICROS;
  }

  @Override
  protected OffsetDateTime getCurrentDateTimeInDifferentZone() {
    return this.getCurrentDateTimeInUtc();
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-openjpa-postgres";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return PostgresConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
