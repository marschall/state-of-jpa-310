package com.github.marschall.stateofjpa310;

import java.time.temporal.TemporalUnit;

import org.junit.jupiter.api.Disabled;

import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;
import com.github.marschall.stateofjpa310.configuration.SqlServerConfiguration;


@Disabled
class SqlServerOpenJpaTest extends AbstractStateOfJpa310Test {

  @Override
  protected int getDefaultNanoSecond() {
    return 123_456_700;
  }

  @Override
  protected TemporalUnit getTimestampResolution() {
    return HundredNanoseconds.INSTANCE;
  }

  @Override
  protected TemporalUnit getTimeResolution() {
    return HundredNanoseconds.INSTANCE;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-openjpa-sqlserver";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return SqlServerConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
