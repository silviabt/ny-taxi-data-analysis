package com.nytaxi.dataanalysis.mapper;

import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.domain.Zone;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Year;

@Component
public class ResultMapper {

    public Result mapTo(Row peek) {
        if (peek.get(1) == null || peek.get(2) == null || peek.get(3) == null || peek.get(4) == null) {
            return Result.builder().build();
        }
        LocalDateTime peekHour = Year.of(peek.getInt(1)).atMonth(peek.getInt(2))
                .atDay(peek.getInt(3)).atTime(peek.getInt(4), 0);

        return Result.builder()
                .peekHour(peekHour.toString())
                .build();
    }

    public Result mapTo(Row peek, Zone zone) {
        Result result = mapTo(peek);
        result.setZone(zone);

        return result;
    }
}
