package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.EclipseLinkConfiguration;
import com.github.marschall.stateofjpa310.configuration.FirebirdConfiguration;

class FirebirdEclipseLinkTest extends AbstractStateOfJpa310Test {

  @Override
  protected boolean offsetDateTimeSupported() {
    return false;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-eclipselink-firebird";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return FirebirdConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return EclipseLinkConfiguration.class;
  }

}
