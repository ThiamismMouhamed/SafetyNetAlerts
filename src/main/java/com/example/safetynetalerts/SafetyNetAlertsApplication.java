package com.example.safetynetalerts;

import com.example.safetynetalerts.service.dataStorage.DataReaderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class SafetyNetAlertsApplication {

    public static void main(String[] args) throws IOException {

        ApplicationContext applicationContext = (ApplicationContext) SpringApplication.run(SafetyNetAlertsApplication.class, args);
        DataReaderService dataReaderService = applicationContext.getBean("dataReaderService", DataReaderService.class);
        dataReaderService.readData();
    }

}
