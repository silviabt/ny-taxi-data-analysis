package com.nytaxi.dataanalysis.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public SparkSession sparkSession(ConfigurationValues configurationValues) {
        return SparkSession
                .builder()
                .appName(configurationValues.getSparkAppName())
                .master(configurationValues.getSparkMaster())
                .getOrCreate();
    }
}
