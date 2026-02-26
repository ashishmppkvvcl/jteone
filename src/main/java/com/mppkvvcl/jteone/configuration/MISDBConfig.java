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
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "misEntityManagerFactory", basePackages = {"com.mppkvvcl.misdao.repositories"}, transactionManagerRef = "misTransactionManager")
@ComponentScan(basePackages = {"com.mppkvvcl.misdao.daos"})
@EnableConfigurationProperties(MISDBProperties.class)
public class MISDBConfig {

    @Primary
    @Bean(name = "misDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mis")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "misEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean misEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("misDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource).packages("com.mppkvvcl.misdao.beans", "com.mppkvvcl.misdao.dtos").persistenceUnit("mis").build();
    }

    @Primary
    @Bean(name = "misTransactionManager")
    public PlatformTransactionManager misTransactionManager(@Qualifier("misEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
