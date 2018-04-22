package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.EclipseLinkConfiguration;
import com.github.marschall.stateofjpa310.configuration.SqlServerConfiguration;

class SqlServerEclipseLinkStateOfJpa310Test extends AbstractStateOfJpa310Test {

  @Override
  protected int getDefaultNanoSecond() {
    return 123_456_700;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-eclipselink-sqlserver";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return SqlServerConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return EclipseLinkConfiguration.class;
  }

}
