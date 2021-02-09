package com.nytaxi.dataanalysis.mapper;

import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.domain.Zone;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ResultMapper {

    private final static String UTC_TIMEZONE = "UTC";

    public Result mapTo(Row peek) {
        return Result.builder()
                .peekHour(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm")
                                .format(ZonedDateTime
                                        .ofInstant(peek.getTimestamp(1).toInstant(),
                                                ZoneId.of(UTC_TIMEZONE))))
                .build();
    }

    public Result mapTo(Row peek, Zone zone) {
        Result result = mapTo(peek);
        result.setZone(zone);

        return result;
    }
}
