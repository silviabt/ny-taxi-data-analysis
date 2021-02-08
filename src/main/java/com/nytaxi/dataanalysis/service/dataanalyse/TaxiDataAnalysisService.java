package com.nytaxi.dataanalysis.service.dataanalyse;

import com.nytaxi.dataanalysis.domain.AnalysisResult;
import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.exception.DataAnalysisException;
import com.nytaxi.dataanalysis.mapper.ResultMapper;
import com.nytaxi.dataanalysis.service.FileHelperService;
import com.nytaxi.dataanalysis.service.JsonWriterService;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.nytaxi.dataanalysis.service.dataanalyse.DataAnalysisUtil.*;
import static org.apache.spark.sql.functions.*;

@Service
public class TaxiDataAnalysisService {

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private JsonWriterService jsonWriterService;

    @Autowired
    private FileHelperService fileHelperService;

    @Autowired
    private ResultMapper resultMapper;

    public AnalysisResult findPeekHour(String filePath) {
        String[] paths = fileHelperService.getFilesPaths(filePath);
        DataFrameReader dataFrameReader = sparkSession.read();
        Dataset<Row> taxiTrips = dataFrameReader.parquet(paths);

        Row[] aggPeekResult = (Row[]) taxiTrips
                .groupBy(GROUP_BY_PICKUP_DATETIME_COLS)
                .agg(COUNT_TRIPS_AGG)
                .select(SELECT_MAX_NO_OF_TRIPS)
                .collect();

        if (aggPeekResult.length < 1) {
            throw new DataAnalysisException("There was a problem when analysing the peek hour for the Taxi NY trips.");
        }

        Row peek = aggPeekResult[0];
        Column condition = year(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(1))
                .and(month(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(2)))
                .and(dayofmonth(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(3)))
                .and(hour(col(PICKUP_DATETIME_COL)).equalTo(peek.getInt(4)));

        String resultPath = DataAnalysisUtil.getResultPath();
        fileHelperService.createResultDir(resultPath);

        taxiTrips
                .where(condition)
                .write()
                .format(PARQUET)
                .save(resultPath + "/result.parquet");

        Result result = resultMapper.mapTo(peek);

        jsonWriterService.writeResultToFile(result, resultPath);

        return AnalysisResult.builder()
                .result(result)
                .resultLocation(resultPath)
                .build();
    }
}
