package com.nytaxi.dataanalysis.service;

import com.nytaxi.dataanalysis.domain.Zone;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class ZoneServiceIT {

    @Autowired
    private ZoneService zoneService;

    @Test
    public void test_loadZones() {
        // Arrange
        String inputFile = "src/test/resources/test_zones.csv";

        // Act
        List<Zone> zoneList = zoneService.loadZones(inputFile);

        // Assert
        assertThat(zoneList).isNotNull();
        assertThat(zoneList.size()).isEqualTo(1);
        assertThat(zoneList.get(0).getLocationId()).isEqualTo("1");
        assertThat(zoneList.get(0).getBorough()).isEqualTo("EWR");
        assertThat(zoneList.get(0).getZone()).isEqualTo("Newark Airport");
        assertThat(zoneList.get(0).getServiceZone()).isEqualTo("EWR");
    }

}
