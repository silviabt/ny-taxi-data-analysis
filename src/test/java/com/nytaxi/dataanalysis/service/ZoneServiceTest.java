package com.nytaxi.dataanalysis.service;

import com.nytaxi.dataanalysis.domain.Zone;
import org.apache.spark.api.java.JavaRDD;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ZoneServiceTest {

    @InjectMocks
    private ZoneService zoneService;

    @Test
    public void test_mapToZone() {
        // Arrange
        String input = "1,\"Manhattan\",\"Penn Station/Madison Sq West\",\"Yellow Zone\"";

        // Act
        Zone result = zoneService.mapToZone(input);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getLocationId()).isEqualTo("1");
        assertThat(result.getBorough()).isEqualTo("Manhattan");
        assertThat(result.getZone()).isEqualTo("Penn Station/Madison Sq West");
        assertThat(result.getServiceZone()).isEqualTo("Yellow Zone");
    }

    @Test
    public void test_mapToZone_whenMissingData() {
        // Arrange
        String input = "1,\"Queens\",\"Woodside\"";

        // Act
        Zone result = zoneService.mapToZone(input);

        // Assert
        assertThat(result).isNull();
    }

    @Test
    public void test_isNotHeaderOrUnknown() {
        // Arrange
        String input = "1,\"Manhattan\",\"Yorkville West\",\"Yellow Zone\"";

        // Act
        boolean result = zoneService.isNotHeaderOrUnknown(input);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isTrue();
    }

    @Test
    public void test_isNotHeaderOrUnknown_whenHeader() {
        // Arrange
        String input = "\"LocationID\",\"Borough\",\"Zone\",\"service_zone\"";

        // Act
        boolean result = zoneService.isNotHeaderOrUnknown(input);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isFalse();
    }

    @Test
    public void test_isNotHeaderOrUnknown_whenUnknown() {
        // Arrange
        String input = "1,\"Unknown\",\"NA\",\"N/A\"";

        // Act
        boolean result = zoneService.isNotHeaderOrUnknown(input);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isFalse();
    }
}
