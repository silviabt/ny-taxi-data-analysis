package com.nytaxi.dataanalysis.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Value("${spark.master}")
    private String sparkMaster;

    @Value("${spark.appName}")
    private String sparkAppName;

    @Bean
    public SparkSession sparkSession() {
        return SparkSession
                .builder()
                .appName(sparkAppName)
                .master(sparkMaster)
                .getOrCreate();
    }
}
