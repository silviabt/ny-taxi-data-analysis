package com.nytaxi.dataanalysis.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ConfigurationValues {

    @Value("${spark.master}")
    private String sparkMaster;

    @Value("${spark.appName}")
    private String sparkAppName;

    @Value("${data.location.path}")
    private String dataLocationPath;
}
