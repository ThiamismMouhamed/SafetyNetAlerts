package com.example.safetynetalerts.service.dataStorage;

import com.example.safetynetalerts.model.Data;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
@Component
@Log4j2
public class DataReaderService {

    private  Data data;

    public Data getData() {
        return data;
    }

    /**
     * Instantiates a new Data storage.
     *
     * @throws IOException the io exception
     */

    public void readData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new ClassPathResource("data.json").getFile();
        log.info("File address = " + file.getAbsolutePath());
        this.data = objectMapper.readValue(file, Data.class);
    }
}
