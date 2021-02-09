package com.nytaxi.dataanalysis.service.dataanalyse;

import org.apache.spark.sql.Column;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.apache.spark.sql.functions.*;

public class DataAnalysisUtil {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm");

    public static final String PARQUET = "parquet";
    public static final String PICKUP_DATETIME_COL = "pickup_datetime";
    public static final String PICKUP_ZONE_COL = "pickup_taxizone_id";
    public static final String DATE_TRUNC_FORMAT = "hour";

    public static final String DATE_COL = "date";

    public static final String TRIPS = "trips";
    public static final String TRIP_ID_COL = "trip_id";

    public static final Column[] GROUP_BY_PICKUP_DATETIME = {
            date_trunc(DATE_TRUNC_FORMAT, col(PICKUP_DATETIME_COL)).as(DATE_COL)
    };

    public static final Column[] GROUP_BY_PICKUP_DATETIME_AND_ZONE_COLS = {
            col(PICKUP_ZONE_COL),
            date_trunc(DATE_TRUNC_FORMAT, col(PICKUP_DATETIME_COL)).as(DATE_COL)
    };

    public static final Column COUNT_TRIPS_AGG = count(TRIP_ID_COL).as(TRIPS);

    public static final Column[] SELECT_MAX_NO_OF_TRIPS = {
            max(TRIPS),
            first(col(DATE_COL))
    };

    public static final Column[] SELECT_MAX_NO_OF_TRIPS_WITH_ZONE = {
            max(TRIPS),
            first(col(DATE_COL)),
            first(col(PICKUP_ZONE_COL)),
    };

    public static String getResultPath() {
       return String.format("result-%s", DATE_FORMAT.format(LocalDateTime.now()));
    }
}
