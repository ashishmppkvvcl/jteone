package com.mppkvvcl.jteone.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "ngbEntityManagerFactory", basePackages = {"com.mppkvvcl.ngbdao.repositories"}, transactionManagerRef = "ngbTransactionManager")
@ComponentScan(basePackages = {"com.mppkvvcl.ngbdao.daos"})
@EnableConfigurationProperties(NGBDBProperties.class)
public class NGBDBConfig {

    @Bean(name = "ngbDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.ngb")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "ngbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean ngbEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("ngbDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.mppkvvcl.ngbdao.beans", "com.mppkvvcl.ngbdao.dtos").persistenceUnit("ngb").build();
    }

    @Bean(name = "ngbTransactionManager")
    public PlatformTransactionManager ngbTransactionManager(@Qualifier("ngbEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
