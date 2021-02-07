package com.nytaxi.dataanalysis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nytaxi.dataanalysis.domain.Result;
import com.nytaxi.dataanalysis.exception.DataAnalysisException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class JsonWriterService {

    public boolean writeResultToFile(Result result, String filePath) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            File resultFile = new File(filePath + "/result.json");
            resultFile.createNewFile();

            mapper.writeValue(resultFile, result);
        } catch (IOException e) {
            throw new DataAnalysisException("There was a problem when writing result to json file.");
        }

        return true;
    }
}
