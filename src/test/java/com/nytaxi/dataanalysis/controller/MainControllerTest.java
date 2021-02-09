package com.nytaxi.dataanalysis.controller;

import com.nytaxi.dataanalysis.config.ConfigurationValues;
import com.nytaxi.dataanalysis.domain.AnalysisResult;
import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.service.dataanalyse.TaxiDataAnalysisService;
import com.nytaxi.dataanalysis.service.dataanalyse.TaxiZoneDataAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MainControllerTest {

    @InjectMocks
    private MainController mainController;

    @Mock
    private TaxiDataAnalysisService taxiDataAnalysisService;

    @Mock
    private ConfigurationValues configurationValues;

    @Mock
    private TaxiZoneDataAnalysisService taxiZoneDataAnalysisService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    public void home() throws Exception {
        // Arrange
        String urlTemplate = "/data-analysis";
        String expectedViewName = "home";

        // Act && Assert
        this.mockMvc.perform(get(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void findPeek() throws Exception {
        // Arrange
        String urlTemplate = "/peak-hour";
        String expectedViewName = "result";

        String locationPath = "/test";
        when(configurationValues.getDataLocationPath()).thenReturn(locationPath);
        when(taxiDataAnalysisService.findPeekHour(locationPath))
                .thenReturn(anAnalysisResult());

        // Act & Assert
        mockMvc.perform(post(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("result"))
                .andExpect(model().attributeExists("resultLocation"))
                .andExpect(view().name(expectedViewName));
    }

    @Test
    public void test_findPeekHourForAllZones() throws Exception {
        // Arrange
        String urlTemplate = "/peak-hour-per-zone";
        String expectedViewName = "resultForZone";

        String locationPath = "locationPath";
        String zonesFilePath = "zonesFilePath";

        when(configurationValues.getDataLocationPath()).thenReturn(locationPath);
        when(configurationValues.getDataZonesLocationPath()).thenReturn(zonesFilePath);
        when(taxiZoneDataAnalysisService.findPeekHourForAllZones(locationPath, zonesFilePath))
                .thenReturn(aResult());

        // Act & Assert
        mockMvc.perform(post(urlTemplate))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("peekHourForZone"))
                .andExpect(view().name(expectedViewName));
    }

    private AnalysisResult anAnalysisResult() {
        return AnalysisResult.builder()
                .result(aResult())
                .resultLocation("result")
                .build();
    }

    private Result aResult() {
        return Result.builder()
                .peakHour("peekHour")
                .build();
    }
}
