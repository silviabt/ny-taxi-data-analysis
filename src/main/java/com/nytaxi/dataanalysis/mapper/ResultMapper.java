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
