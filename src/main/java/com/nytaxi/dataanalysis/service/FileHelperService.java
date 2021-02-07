package com.nytaxi.dataanalysis.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;

import static com.nytaxi.dataanalysis.service.dataanalyse.DataAnalysisUtil.RESULT_PATH;

@Service
public class FileHelperService {

    public void createResultDir() {
        File theDir = new File(RESULT_PATH);
        if (!theDir.exists()){
            theDir.mkdirs();
        }
    }

    public String[] getFilesPaths(String filePath) {
        return Stream.of(Objects.requireNonNull(new File(filePath).list()))
                .map(path -> filePath + path)
                .toArray(String[]::new);
    }
}
