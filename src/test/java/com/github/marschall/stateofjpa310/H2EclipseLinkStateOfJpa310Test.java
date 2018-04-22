package com.github.marschall.stateofjpa310;

class H2EclipseLinkStateOfJpa310Test extends AbstractStateOfJpa310Test {

  @Override
  protected String getPersistenceUnitName() {
    return "state-of-jpa-310-eclipselink-h2";
  }

}
