package com.optimagrowth.license;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class LicensingServiceApplication {

    @Value("${local.lang}")
    private String local;

    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApplication.class, args);
    }

   /* @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        *//***
         * Sets US as the default locale
        *//*
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }*/

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        /***
         * Doesn’t throw an error if a message isn’t found, instead it returns the message code
        */
        messageSource.setUseCodeAsDefaultMessage(true);
        /***
         * Sets the base name of the languages properties files 
         */
        messageSource.setBasenames("messages");
        /*messageSource.setDefaultLocale(Locale.US);*/
        messageSource.setDefaultLocale(Locale.forLanguageTag(local));
        return messageSource;
    }

    /***
     * build the licensing service as an executable JAR and then start the JAR from the command line :
     * mvn clean package && java -jar target/licensing-service-0.0.1-SNAPSHOT.jar 
     */

}
