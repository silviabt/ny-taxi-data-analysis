package com.nytaxi.dataanalysis.service;

import com.nytaxi.dataanalysis.exception.DataAnalysisException;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;

import static com.nytaxi.dataanalysis.service.DataAnalysisUtil.*;
import static org.apache.spark.sql.functions.*;

@Service
public class DataAnalysisService {

    @Autowired
    private SparkSession sparkSession;

    public void findPeekHour(String filePath) {
        String[] paths = getFilesPaths(filePath);
        DataFrameReader dataFrameReader = sparkSession.read();
        Dataset<Row> taxiTrips = dataFrameReader.parquet(paths);

        Row[] aggMaxResult = (Row[]) taxiTrips
                .groupBy(GROUP_BY_COLUMNS)
                .agg(COUNT_TRIPS_AGG)
                .select(SELECT_MAX_NO_OF_TRIPS)
                .collect();

        if (aggMaxResult.length < 1) {
            throw new DataAnalysisException("There was a problem when analysing peek hour for the Taxi NY trips.");
        }

        Row peek = aggMaxResult[0];
        Column condition = month(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(1))
                .and(year(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(2)))
                .and(hour(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(3)));

        taxiTrips
                .filter(condition)
                .write()
                .format(PARQUET)
                .save(RESULT_PATH);
    }

    private String[] getFilesPaths(String filePath) {
        return Stream.of(Objects.requireNonNull(new File(filePath).list()))
                .map(path -> filePath + path)
                .toArray(String[]::new);
    }
}
