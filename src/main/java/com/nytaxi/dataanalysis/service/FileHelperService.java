package com.nytaxi.dataanalysis.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class FileHelperService {

    public boolean createResultDir(String path) {
        File theDir = new File(path);
        if (!theDir.exists()) {
            return theDir.mkdirs();
        }
        return false;
    }

    public String[] getFilesPaths(String filePath) {
        return Stream.of(Objects.requireNonNull(new File(filePath).list()))
                .map(path -> filePath + path)
                .toArray(String[]::new);
    }
}
