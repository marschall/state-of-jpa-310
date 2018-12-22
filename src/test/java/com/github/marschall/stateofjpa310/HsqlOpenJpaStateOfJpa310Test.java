package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.HsqlConfiguration;
import com.github.marschall.stateofjpa310.configuration.OpenJpaConfiguration;

class HsqlOpenJpaStateOfJpa310Test extends AbstractStateOfJpa310Test {

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-openjpa-hsql";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return HsqlConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return OpenJpaConfiguration.class;
  }

}
