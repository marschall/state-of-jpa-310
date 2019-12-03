package com.github.marschall.stateofjpa310;

import org.junit.jupiter.api.Disabled;

import com.github.marschall.stateofjpa310.configuration.HibernateConfiguration;
import com.github.marschall.stateofjpa310.configuration.OracleConfiguration;

@Disabled
class OracleHibernateTest extends AbstractStateOfJpa310Test {

  @Override
  protected boolean timeSupported() {
    return false;
  }

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-hibernate-oracle";
  }

  @Override
  protected Class<?> getDataSourceConfiguration() {
    return OracleConfiguration.class;
  }

  @Override
  protected Class<?> getJapConfiguration() {
    return HibernateConfiguration.class;
  }

}
