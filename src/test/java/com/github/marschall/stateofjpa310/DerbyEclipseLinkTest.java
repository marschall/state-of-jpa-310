package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.DerbyConfiguration;
import com.github.marschall.stateofjpa310.configuration.EclipseLinkConfiguration;

class DerbyEclipseLinkTest extends AbstractStateOfJpa310Test {

  @Override
  protected boolean offsetDateTimeSupported() {
    return false;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-eclipselink-derby";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return DerbyConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return EclipseLinkConfiguration.class;
  }

}
