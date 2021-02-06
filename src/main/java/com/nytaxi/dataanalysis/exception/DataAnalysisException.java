package com.nytaxi.dataanalysis.exception;

import lombok.Getter;

@Getter
public class DataAnalysisException extends RuntimeException {

    private String message;

    public DataAnalysisException(String message) {
        super(message);
        this.message = message;
    }
}
