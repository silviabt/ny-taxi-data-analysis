package com.nytaxi.dataanalysis.controller;

import com.nytaxi.dataanalysis.service.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private DataAnalysisService dataAnalysisService;

    @GetMapping("/home")
    public String home() {
        dataAnalysisService.findPeekHour("D:\\ny-taxi-data\\NYC_taxi_2009-2016.parquet\\");
        return "home";
    }
}
