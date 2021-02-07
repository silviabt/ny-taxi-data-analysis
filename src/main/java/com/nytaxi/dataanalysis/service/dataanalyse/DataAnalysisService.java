package com.nytaxi.dataanalysis.service.dataanalyse;

import com.nytaxi.dataanalysis.domain.AnalysisResult;
import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.exception.DataAnalysisException;
import com.nytaxi.dataanalysis.service.FileHelperService;
import com.nytaxi.dataanalysis.service.JsonWriterService;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Year;

import static com.nytaxi.dataanalysis.service.dataanalyse.DataAnalysisUtil.*;
import static org.apache.spark.sql.functions.*;

@Service
public class DataAnalysisService {

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private JsonWriterService jsonWriterService;

    @Autowired
    private FileHelperService fileHelperService;

    public AnalysisResult findPeekHour(String filePath) {
        String[] paths = fileHelperService.getFilesPaths(filePath);
        DataFrameReader dataFrameReader = sparkSession.read();
        Dataset<Row> taxiTrips = dataFrameReader.parquet(paths);

        Row[] aggMaxResult = (Row[]) taxiTrips
                .groupBy(GROUP_BY_PICKUP_DATETIME_COLS)
                .agg(COUNT_TRIPS_AGG)
                .select(SELECT_MAX_NO_OF_TRIPS)
                .collect();

        if (aggMaxResult.length < 1) {
            throw new DataAnalysisException("There was a problem when analysing the peek hour for the Taxi NY trips.");
        }

        Row peek = aggMaxResult[0];
        Column condition = year(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(1))
                .and(month(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(2)))
                .and(dayofmonth(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(3)))
                .and(hour(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(4)));

        Result result = buildResult(peek);

        fileHelperService.createResultDir(RESULT_PATH);
        jsonWriterService.writeResultToFile(result, RESULT_PATH);

        taxiTrips
                .filter(condition)
                .write()
                .format(PARQUET)
                .save(RESULT_PATH + "/result.parquet");

        return AnalysisResult.builder()
                .result(result)
                .resultLocation(RESULT_PATH)
                .build();
    }

    private Result buildResult(Row peek) {
        LocalDateTime peekHour = Year.of(peek.getInt(1)).atMonth(peek.getInt(2))
                .atDay(peek.getInt(3)).atTime(peek.getInt(4), 0);

        return Result.builder()
                .peekHour(peekHour.toString())
                .build();
    }
}
