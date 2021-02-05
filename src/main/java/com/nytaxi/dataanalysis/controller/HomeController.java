package com.nytaxi.dataanalysis.controller;

import com.nytaxi.dataanalysis.service.SparkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        SparkService.dataAnalyse("D:\\ny-taxi-data\\NYC_taxi_2009-2016.parquet\\", "part-r-00002-ec9cbb65-519d-4bdb-a918-72e2364c144c.snappy.parquet");
        return "home";
    }
}
