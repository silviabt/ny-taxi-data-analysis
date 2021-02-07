package com.nytaxi.dataanalysis.service.dataanalyse;

import org.apache.spark.sql.Column;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.spark.sql.functions.*;

public class DataAnalysisUtil {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

    public static final String PARQUET = "parquet";
    public static final String PICKUP_DATETIME_COL = "pickup_datetime";
    public static final String RESULT_PATH = String.format("result-%s", DATE_FORMAT.format(LocalDateTime.now()));

    public static final String MONTH_COL = "month";
    public static final String YEAR_COL = "year";
    public static final String HOUR_COL = "hour";
    public static final String DAY_COL = "day";

    public static final String NO_TRIPS_COL = "trips";
    public static final String TRIP_ID_COL = "trip_id";

    public static final Column[] GROUP_BY_PICKUP_DATETIME_COLS = {
            month(col(PICKUP_DATETIME_COL)).as(MONTH_COL),
            year(col(PICKUP_DATETIME_COL)).as(YEAR_COL),
            dayofmonth(col(PICKUP_DATETIME_COL)).as(DAY_COL),
            hour(col(PICKUP_DATETIME_COL)).as(HOUR_COL)
    };

    public static final Column COUNT_TRIPS_AGG = count(TRIP_ID_COL).as(NO_TRIPS_COL);

    public static final Column[] SELECT_MAX_NO_OF_TRIPS = {
            max(NO_TRIPS_COL),
            first(col(YEAR_COL)),
            first(col(MONTH_COL)),
            first(col(DAY_COL)),
            first(col(HOUR_COL))
    };
}
