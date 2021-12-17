package com.github.marschall.stateofjpa310;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.junit.jupiter.api.Disabled;

import com.github.marschall.stateofjpa310.configuration.Db2Configuration;
import com.github.marschall.stateofjpa310.configuration.EclipseLinkConfiguration;

@Disabled
class Db2EclipseLinkTest extends AbstractStateOfJpa310Test {

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
    return "state-of-jpa-310-eclipselink-db2";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return Db2Configuration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return EclipseLinkConfiguration.class;
  }

}
