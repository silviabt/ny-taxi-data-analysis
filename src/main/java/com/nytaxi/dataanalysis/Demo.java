package com.nytaxi.dataanalysis;

import com.nytaxi.dataanalysis.service.SparkService;

public class Demo {

    public static void main(String[] args) {
        SparkService.dataAnalyse("D:\\ny-taxi-data\\NYC_taxi_2009-2016.parquet\\", "part-r-00002-ec9cbb65-519d-4bdb-a918-72e2364c144c.snappy.parquet");
    }
}
