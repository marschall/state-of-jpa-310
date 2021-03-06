package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.EclipseLinkConfiguration;
import com.github.marschall.stateofjpa310.configuration.OracleConfiguration;

class OracleEclipseLinkTest extends AbstractStateOfJpa310Test {

  @Override
  protected boolean timeSupported() {
    return false;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-eclipselink-oracle";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return OracleConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return EclipseLinkConfiguration.class;
  }

}
