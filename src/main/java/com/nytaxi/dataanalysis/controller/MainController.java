package com.nytaxi.dataanalysis.controller;

import com.nytaxi.dataanalysis.config.ConfigurationValues;
import com.nytaxi.dataanalysis.domain.AnalysisResult;
import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.service.dataanalyse.TaxiDataAnalysisService;
import com.nytaxi.dataanalysis.service.dataanalyse.TaxiZoneDataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private TaxiDataAnalysisService taxiDataAnalysisService;

    @Autowired
    private ConfigurationValues configurationValues;

    @Autowired
    private TaxiZoneDataAnalysisService taxiZoneDataAnalysisService;

    @GetMapping("/data-analysis")
    public String home() {
        return "home";
    }

    @PostMapping("/peek-hour")
    public String findPeek(Model model) {
        AnalysisResult peekHour = taxiDataAnalysisService.findPeekHour(configurationValues.getDataLocationPath());
        model.addAttribute("result", peekHour.getResult());
        model.addAttribute("resultLocation", peekHour.getResultLocation());
        return "result";
    }

    @PostMapping("/peek-hour-per-zone")
    public String findPeekHourForAllZones(Model model) {
        Result peekHourForZone = taxiZoneDataAnalysisService
                .findPeekHourForAllZones(configurationValues.getDataLocationPath(),
                        configurationValues.getDataZonesLocationPath());
        model.addAttribute("peekHourForZone", peekHourForZone);
        return "resultForZone";
    }
}
