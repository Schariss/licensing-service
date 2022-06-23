package com.optimagrowth.license.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "example")
@Getter @Setter
public class ServiceConfig{

    // map fields to config properties with prefix
    private String property;
    // private String lang;
}