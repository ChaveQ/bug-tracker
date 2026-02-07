package ca.bakuryn.bugtracker.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "ca.bakuryn.bugtracker")
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class PersistenceConfig {

  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;
  @Value("${JDBC_URL:${spring.datasource.url}}")
  private String url;
  @Value("${DB_USERNAME:${spring.datasource.username}}")
  private String username;
  @Value("${DB_PASSWORD:${spring.datasource.password}}")
  private String password;
  @Value("${jpa.hibernate.ddl-auto}")
  private String ddlAuto;
  @Value("${jpa.properties.hibernate.dialect}")
  private String dialect;

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  public DataSource dataSource() {
    HikariConfig config = new HikariConfig();
    config.setDriverClassName(driverClassName);
    config.setJdbcUrl(url);
    config.setUsername(username);
    config.setPassword(password);
    return new HikariDataSource(config);
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setPackagesToScan("ca.bakuryn.bugtracker.persistence.entity");
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", dialect);
    properties.setProperty("hibernate.hbm2ddl.auto", ddlAuto);
    sessionFactory.setHibernateProperties(properties);
    return sessionFactory;
  }

  @Bean
  public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
    return new HibernateTransactionManager(sessionFactory);
  }
}
