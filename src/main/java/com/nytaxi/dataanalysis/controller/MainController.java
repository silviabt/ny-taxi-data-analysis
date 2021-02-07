package com.nytaxi.dataanalysis.controller;

import com.nytaxi.dataanalysis.config.ConfigurationValues;
import com.nytaxi.dataanalysis.domain.AnalysisResult;
import com.nytaxi.dataanalysis.service.dataanalyse.DataAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private DataAnalysisService dataAnalysisService;

    @Autowired
    private ConfigurationValues configurationValues;

    @GetMapping("/data-analysis")
    public String home() {
        return "home";
    }

    @PostMapping("/peek-hour")
    public String findPeek(Model model) {
        AnalysisResult peekHour = dataAnalysisService.findPeekHour(configurationValues.getDataLocationPath());
        model.addAttribute("result", peekHour.getResult());
        model.addAttribute("resultLocation", peekHour.getResultLocation());
        return "result";
    }
}
