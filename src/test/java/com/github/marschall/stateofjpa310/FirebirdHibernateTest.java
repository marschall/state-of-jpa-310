package com.github.marschall.stateofjpa310;

import com.github.marschall.stateofjpa310.configuration.FirebirdConfiguration;
import com.github.marschall.stateofjpa310.configuration.HibernateConfiguration;

class FirebirdHibernateTest extends AbstractStateOfJpa310Test {

  @Override
  protected boolean offsetDateTimeSupported() {
    return false;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-hibernate-firebird";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return FirebirdConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return HibernateConfiguration.class;
  }

}
