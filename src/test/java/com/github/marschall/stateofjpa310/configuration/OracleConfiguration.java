package com.github.marschall.stateofjpa310.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

@Configuration
public class OracleConfiguration {

  @Bean
  public DataSource dataSource() {
    // oracle.jdbc.OracleDriver
    SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
    dataSource.setSuppressClose(true);
//  dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:ORCLCDB");
    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521/ORCLPDB1");
    dataSource.setUsername("c##jdbc");
    dataSource.setPassword("Cent-Quick-Space-Bath-8");
    return dataSource;
  }

}
