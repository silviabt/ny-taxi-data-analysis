package com.nytaxi.dataanalysis.controller;

import com.nytaxi.dataanalysis.service.dataanalyse.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private DataAnalysisService dataAnalysisService;

    @GetMapping("/home")
    public String home() {
        dataAnalysisService.findPeekHour("D:\\test\\");
        return "home";
    }
}
