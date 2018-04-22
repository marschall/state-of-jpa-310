package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.EclipseLinkConfiguration;
import com.github.marschall.stateofjpa310.configuration.H2Configuration;

class H2EclipseLinkStateOfJpa310Test extends AbstractStateOfJpa310Test {

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-eclipselink-h2";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return H2Configuration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return EclipseLinkConfiguration.class;
  }

}
