package com.github.marschall.stateofjpa310;

class H2HibernateStateOfJpa310Test extends AbstractStateOfJpa310Test {

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-hibernate-h2";
  }

}
