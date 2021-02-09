package com.nytaxi.dataanalysis.service;

import com.nytaxi.dataanalysis.domain.Result;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JsonWriterServiceTest {

    private JsonWriterService jsonWriterService;

    @BeforeEach
    public void setUp() {
        jsonWriterService = new JsonWriterService();
    }

    @Test
    public void writeResultToFile() {
        // Arrange
        Result result = Result.builder()
                .peakHour("test")
                .build();
        String filePath = "target";

        // Act
        boolean created = jsonWriterService.writeResultToFile(result, filePath);

        // Assert
        assertThat(created).isTrue();
    }

    @AfterEach
    public void tearDown() {
        File fileToClean = new File("target/result.json");
        if (fileToClean.exists()) {
            fileToClean.delete();
        }
    }
}
