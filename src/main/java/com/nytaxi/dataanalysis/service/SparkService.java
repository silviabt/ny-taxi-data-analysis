package com.nytaxi.dataanalysis.service;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.stereotype.Service;

@Service
public class SparkService {

    public static void dataAnalyse(String filePath, String fileName) {
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .master("local")
                .getOrCreate();

        Dataset<Row> parquetFileDf = spark.read().parquet(filePath + fileName);
        parquetFileDf.createOrReplaceTempView("parquetFile");


        Dataset<Row> namesDF = spark.sql("SELECT payment_type FROM parquetFile WHERE dropoff_taxizone_id = 19");
        Dataset<String> namesDS = namesDF.map(
                (MapFunction<Row, String>) row -> "Payment: " + row.getString(0),
                Encoders.STRING());
        namesDS.show();
        System.out.println(namesDF.count());
    }
}
