package com.nytaxi.dataanalysis.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Result {

    private String peakHour;

    @JsonIgnore
    private Zone zone;
}
