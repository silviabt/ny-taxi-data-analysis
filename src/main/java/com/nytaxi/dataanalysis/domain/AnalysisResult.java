package com.nytaxi.dataanalysis.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnalysisResult {

    private Result result;
    private String resultLocation;
}
