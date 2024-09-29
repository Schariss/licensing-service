package com.optimagrowth.license.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "vault")
public record VaultParams (String name, String pwd) {
}