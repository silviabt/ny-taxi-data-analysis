package com.nytaxi.dataanalysis.service;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;

import static org.apache.spark.sql.functions.*;

@Service
public class DataAnalysisService {

    private static final String PARQUET = "parquet";

    @Autowired
    private SparkSession sparkSession;

    public void findPeekHour(String filePath) {
        Logger.getLogger("org").setLevel(Level.ERROR);

        String[] paths = getFilesPaths(filePath);
        DataFrameReader dataFrameReader = sparkSession.read();

        Dataset<Row> taxiTrips = dataFrameReader.parquet(paths);

        Column[] groupByColumns = {
//                col("pickup_taxizone_id"),
                month(col("pickup_datetime")).as("month"),
                year(col("pickup_datetime")).as("year"),
                hour(col("pickup_datetime")).as("hour")
        };

        Column countTrips = count("trip_id").as("trips");

        Column[] selectMaxNoOfTrips = {
                max("trips"),
                first(col("month")),
                first(col("year")),
                first(col("hour"))
        };

        Row peek = ((Row[]) taxiTrips
//                .filter(col("pickup_taxizone_id").equalTo("19"))
                .groupBy(groupByColumns)
                .agg(countTrips)
                .select(selectMaxNoOfTrips)
                .collect())[0];

        int month = peek.getInt(1);
        int year = peek.getInt(2);
        int hour = peek.getInt(3);

        taxiTrips
                .filter(month(col("pickup_datetime")).equalTo(month)
                        .and(year(col("pickup_datetime")).equalTo(year))
                        .and(hour(col("pickup_datetime")).equalTo(hour))
                )
                .write()
                .format(PARQUET)
                .save("result.parquet");
    }

    private String[] getFilesPaths(String filePath) {
        return Stream.of(Objects.requireNonNull(new File(filePath).list()))
                .map(path -> filePath + path)
                .toArray(String[]::new);
    }
}
