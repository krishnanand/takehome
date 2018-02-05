package com.krishnanand.rest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration object that encapsulates database credentials.
 * 
 * @author krishnanand (Kartik Krishnanand)
 */
@Configuration
@PropertySource(value="classpath:application.properties", ignoreResourceNotFound=true)
public class ConfigurationProperties {
  
  @Value("${spring.datasource.driver-class-name}")
  private String driverClass;
  
  @Value("${spring.datasource.url}")
  private String url;
  
  @Value("${spring.datasource.username}")
  private String username;
  
  @Value("${spring.datasource.password}")
  private String password;
  
  public String getDriverClass() {
    return driverClass;
  }

  public String getUrl() {
    return url;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  /**
   * Instantiates a datasource.
   */
  @Bean()
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName(this.driverClass);
    dataSource.setUrl(this.url);
    dataSource.setUsername(this.username);
    dataSource.setPassword(this.password);
    return dataSource;
  }
  
  /**
   * Initialises a restful template.
   */
  @Bean
  RestTemplate restTemplate() {
    return new RestTemplate();
  }
  
  /**
   * Enables the in memory local database to have a relative path.
   */
  @PostConstruct
  void setH2DbProperties() {
    System.setProperty("h2.implicitRelativePath", "true");
  }
  
  /**
   * Resets the in memory local database configuration.
   */
  @PreDestroy
  void removeH2DbProperties() {
    System.clearProperty("h2.implicitRelativePath");
  }

}
