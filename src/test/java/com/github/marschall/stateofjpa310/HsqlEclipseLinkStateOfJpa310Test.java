package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.EclipseLinkConfiguration;
import com.github.marschall.stateofjpa310.configuration.HsqlConfiguration;

class HsqlEclipseLinkStateOfJpa310Test extends AbstractStateOfJpa310Test {

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-eclipselink-hsql";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return HsqlConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return EclipseLinkConfiguration.class;
  }

}
