package com.nytaxi.dataanalysis.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    private final static String SPARK_SQL_IN_MEMORY_COLUMNAR_STORAGE = "spark.sql.inMemoryColumnarStorage.batchSize";
    private final static String SPARK_SQL_PARQUET_OUTPUT_TIMESTAMP_TYPE = "spark.sql.parquet.outputTimestampType";
    private final static String SPARK_SQL_SESSION_TIMEZONE = "spark.sql.session.timeZone";
    private final static String TIMESTAMP_TYPE = "INT96";
    private final static String UTC_TIMEZONE = "UTC";

    @Bean
    public SparkSession sparkSession(ConfigurationValues configurationValues) {
        return SparkSession
                .builder()
                .appName(configurationValues.getSparkAppName())
                .master(configurationValues.getSparkMaster())
                .config(SPARK_SQL_IN_MEMORY_COLUMNAR_STORAGE, 10000)
                .config(SPARK_SQL_PARQUET_OUTPUT_TIMESTAMP_TYPE, TIMESTAMP_TYPE)
                .config(SPARK_SQL_SESSION_TIMEZONE, UTC_TIMEZONE)
                .getOrCreate();
    }
}
