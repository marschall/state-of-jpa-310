package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.H2Configuration;
import com.github.marschall.stateofjpa310.configuration.HibernateConfiguration;

class H2HibernateStateOfJpa310Test extends AbstractStateOfJpa310Test {

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-hibernate-h2";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return H2Configuration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return HibernateConfiguration.class;
  }

}
