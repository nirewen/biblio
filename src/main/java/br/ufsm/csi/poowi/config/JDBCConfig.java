package br.ufsm.csi.poowi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JDBCConfig {
    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource d = new DriverManagerDataSource();

        d.setDriverClassName(this.driverClassName);
        d.setUrl(this.url);
        d.setUsername(this.username);
        d.setPassword(this.password);

        return d;
    }
}
