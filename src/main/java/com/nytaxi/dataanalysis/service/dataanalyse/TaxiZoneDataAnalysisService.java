package com.nytaxi.dataanalysis.service.dataanalyse;

import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.domain.Zone;
import com.nytaxi.dataanalysis.exception.DataAnalysisException;
import com.nytaxi.dataanalysis.mapper.ResultMapper;
import com.nytaxi.dataanalysis.service.FileHelperService;
import com.nytaxi.dataanalysis.service.ZoneService;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nytaxi.dataanalysis.service.dataanalyse.DataAnalysisUtil.*;

@Service
public class TaxiZoneDataAnalysisService {

    @Autowired
    private SparkSession sparkSession;

    @Autowired
    private FileHelperService fileHelperService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private ResultMapper resultMapper;

    public Result findPeekHourForAllZones(String filePath, String zonesFilePath) {
        String[] paths = fileHelperService.getFilesPaths(filePath);

        DataFrameReader dataFrameReader = sparkSession.read();
        Dataset<Row> taxiTrips = dataFrameReader.parquet(paths);

        Row[] aggMaxResult = (Row[]) taxiTrips
                .groupBy(GROUP_BY_PICKUP_DATETIME_AND_ZONE_COLS)
                .agg(COUNT_TRIPS_AGG)
                .select(SELECT_MAX_NO_OF_TRIPS_WITH_ZONE)
                .collect();

        if (aggMaxResult.length < 1) {
            throw new DataAnalysisException("There was a problem when analysing the peek hour for the Taxi NY trips.");
        }

        List<Zone> zones = zoneService.loadZones(zonesFilePath);
        Zone zone = zones.stream()
                .filter(z -> z.getLocationId().equals(String.valueOf(aggMaxResult[0].getInt(5))))
                .findAny()
                .orElse(null);

        return resultMapper.mapTo(aggMaxResult[0], zone);
    }
}
