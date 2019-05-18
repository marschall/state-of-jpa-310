package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.H2Configuration;
import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;

class H2OpenJpaTest extends AbstractStateOfJpa310Test {

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-openjpa-h2";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return H2Configuration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
