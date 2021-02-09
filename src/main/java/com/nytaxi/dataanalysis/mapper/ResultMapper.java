package com.nytaxi.dataanalysis.mapper;

import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.domain.Zone;
import org.apache.spark.sql.Row;
import org.springframework.stereotype.Component;

@Component
public class ResultMapper {

    public Result mapTo(Row peek) {
        return Result.builder()
                .peekHour(peek.getTimestamp(1).toString())
                .build();
    }

    public Result mapTo(Row peek, Zone zone) {
        Result result = mapTo(peek);
        result.setZone(zone);

        return result;
    }
}
