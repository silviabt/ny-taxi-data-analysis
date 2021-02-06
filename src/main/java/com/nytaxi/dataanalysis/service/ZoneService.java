package com.nytaxi.dataanalysis.service;

import com.nytaxi.dataanalysis.domain.Zone;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneService {

    @Autowired
    private SparkSession sparkSession;

    public List<Zone> loadZones(String filePath) {
        JavaSparkContext javaSparkContext = JavaSparkContext.fromSparkContext(sparkSession.sparkContext());
        JavaRDD<String> zoneLookup = javaSparkContext.textFile(filePath);

        List<String> zonesEntries = zoneLookup.filter(ZoneService::isNotHeaderOrUnknown).collect();

        return zonesEntries
                .stream()
                .map(this::mapToZone)
                .collect(Collectors.toList());
    }

    protected Zone mapToZone(String line) {
        String[] data = line.split(",");
        if (data.length < 4) {
            return null;
        }
        return Zone.builder()
                .locationId(data[0])
                .borough(removeDoubleQuotes(data[1]))
                .zone(removeDoubleQuotes(data[2]))
                .serviceZone(removeDoubleQuotes(data[3]))
                .build();
    }

    protected static boolean isNotHeaderOrUnknown(String line) {
        return !line.contains("LocationID") && !line.contains("Unknown");
    }

    protected String removeDoubleQuotes(String input) {
        return input.replace("\"", "");
    }
}
