package com.github.marschall.stateofjpa310.configuration;

import static com.github.marschall.stateofjpa310.Constants.PERSISTENCE_UNIT_NAME;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.DefaultJpaDialect;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.github.marschall.stateofjpa310.configuration.openjpa.OpenJpaDialect;
import com.github.marschall.stateofjpa310.configuration.openjpa.OpenJpaVendorAdapter;

@Configuration
public class OpenJpaConfiguration {

  @Autowired
  private Environment environment;

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManager(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
    bean.setPersistenceUnitName(this.environment.getProperty(PERSISTENCE_UNIT_NAME));
    bean.setJpaDialect(this.jpaDialect());
    bean.setJpaVendorAdapter(new OpenJpaVendorAdapter());
    bean.setDataSource(dataSource);
    return bean;
  }


  @Bean
  public JpaDialect jpaDialect() {
    return new OpenJpaDialect();
  }

}
