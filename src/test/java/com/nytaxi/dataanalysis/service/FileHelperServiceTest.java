package com.nytaxi.dataanalysis.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class FileHelperServiceTest {

    private FileHelperService fileHelperService;

    @BeforeEach
    public void setUp() {
        fileHelperService = new FileHelperService();
    }

    @Test
    public void createResultDir() {
        // Arrange

        // Act
        boolean created = fileHelperService.createResultDir("target/testCreateResultDir");

        // Assert
        assertThat(created).isTrue();
    }

    @Test
    public void getFilesPaths() {
        // Arrange

        // Act
        String[] paths = fileHelperService.getFilesPaths("src/test/resources/test_taxi_trips/");

        // Assert
        assertThat(paths.length).isEqualTo(1);
        assertThat(paths[0]).isEqualTo("src/test/resources/test_taxi_trips/part-for-test.snappy.parquet");
    }

    @AfterEach
    public void tearDown() {
        File toClean = new File("target/testCreateResultDir");
        if (toClean.exists() && toClean.isDirectory()) {
            toClean.delete();
        }
    }
}
