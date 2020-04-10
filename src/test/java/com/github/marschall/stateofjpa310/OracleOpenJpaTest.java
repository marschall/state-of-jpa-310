package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;
import com.github.marschall.stateofjpa310.configuration.OracleConfiguration;

class OracleOpenJpaTest extends AbstractStateOfJpa310Test {

  @Override
  protected boolean timeSupported() {
    return false;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-openjpa-oracle";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return OracleConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
