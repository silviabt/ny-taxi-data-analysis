package com.nytaxi.dataanalysis.service;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class SparkService {

    @Autowired
    private SparkSession sparkSession;

    public void dataAnalyse(String filePath, String fileName) {
        Dataset<Row> parquetFileDf = sparkSession.read().parquet(filePath + fileName);
        parquetFileDf.createOrReplaceTempView("parquetFile");

        Dataset<Row> namesDF = sparkSession.sql("SELECT pickup_datetime FROM parquetFile WHERE dropoff_taxizone_id = 19");
        Dataset<Timestamp> namesDS = namesDF.map(
                (MapFunction<Row, Timestamp>) row -> row.getTimestamp(0),
                Encoders.TIMESTAMP());
        namesDS.show();
        System.out.println("Count:" + namesDF.count());
    }
}
