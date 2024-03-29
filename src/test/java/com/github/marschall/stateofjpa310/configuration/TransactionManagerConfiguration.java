package com.github.marschall.stateofjpa310.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class TransactionManagerConfiguration {

  @Autowired
  private DataSource dataSource;

  @Autowired
  private JpaDialect jpaDialect;

  @Bean
  public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
    transactionManager.setDataSource(this.dataSource);
    transactionManager.setJpaDialect(this.jpaDialect);
    return transactionManager;
  }

}
