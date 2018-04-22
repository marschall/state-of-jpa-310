package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.HibernateConfiguration;
import com.github.marschall.stateofjpa310.configuration.HsqlConfiguration;

class HsqlHibernateStateOfJpa310Test extends AbstractStateOfJpa310Test {

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-hibernate-hsql";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return HsqlConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return HibernateConfiguration.class;
  }

}
