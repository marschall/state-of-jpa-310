package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;
import com.github.marschall.stateofjpa310.configuration.SqlServerConfiguration;

class SqlServerOpenJpaTest extends AbstractStateOfJpa310Test {

  @Override
  protected int getDefaultNanoSecond() {
    return 123_456_700;
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
