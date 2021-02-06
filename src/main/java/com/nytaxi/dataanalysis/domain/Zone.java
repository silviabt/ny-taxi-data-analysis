package com.nytaxi.dataanalysis.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Zone {

    private String locationId;
    private String borough;
    private String zone;
    private String serviceZone;

}
