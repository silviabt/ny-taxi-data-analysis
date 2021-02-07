package com.nytaxi.dataanalysis.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    private final static String SPARK_SQL_IN_MEMORY_COLUMNAR_STORAGE = "spark.sql.inMemoryColumnarStorage.batchSize";

    @Bean
    public SparkSession sparkSession(ConfigurationValues configurationValues) {
        return SparkSession
                .builder()
                .appName(configurationValues.getSparkAppName())
                .master(configurationValues.getSparkMaster())
                .config(SPARK_SQL_IN_MEMORY_COLUMNAR_STORAGE, 10000)
                .getOrCreate();
    }
}
