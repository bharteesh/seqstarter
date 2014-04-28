package org.cirrostratus.samples.java;

import org.cirrostratus.sequoia.persistentvariable.spring.PersistentVarAppConfig;
import org.cirrostratus.sequoia.persistentvariable.spring.PersistentVarConfigurer;
import org.cirrostratus.sequoia.persistentvariable.spring.PersistentVarSettings;
import org.cirrostratus.sequoia.servicelocator.ServiceLocator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

@Configuration("testConfig")
@ComponentScan(basePackages={"org.cirrostratus.sequoia.persistentvariable.spring"}, 
    excludeFilters={@ComponentScan.Filter(value=Configuration.class, type=FilterType.ANNOTATION)})
@PropertySource("classpath:seq.properties")
@PersistentVarSettings(bootstrapZookeeper=true, exclusions={"test.version"}, createPersistentVarBeans=true, enableContextRefresh=true)

public class PersistentVariableConfig extends PersistentVarAppConfig {
    
    @Value("${test.version}")
    private String version;
    
    @Value("${test.prop1}")
    private String prop1;
    @Value("${test.prop2}")
    private String prop2;
    @Value("${test.prop3}")
    private int prop3;
    @Value("${test.prop4}")
    private String prop4;
    
    public String getVersion() {
        return version;
    }

    @Bean
    public ServiceLocator serviceLocator() {
        return new ServiceLocator();
    }
    
    @Configuration
    public static class PvConfig extends PersistentVarConfigurer{}
}
