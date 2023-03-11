package com.example.safetynetalerts.service.dataStorage;


import com.example.safetynetalerts.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Log4j2
public class DataStorageService implements DataStorageIn {

    private final Data data;

    /**
     * Instantiates a new Data storage.
     *
     * @throws IOException the io exception
     */
    public DataStorageService() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new ClassPathResource("data.json").getFile();
        log.info("File address = " + file.getAbsolutePath());
        this.data = objectMapper.readValue(file, Data.class);
    }

    @Override
    public List<Personne> getPersonnes() {
        log.info("Get all persons");
        return data.getPersons();
    }

    @Override
    public Optional<Personne> getPersonneById(Id id) {
        log.info("Get all persons by Id");
        return getPersonnes()
                .stream()
                .filter(person -> person.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Personne> getPersonneByAdresse(String adresse) {
        log.info("Get all persons by address");
        return getPersonnes()
                .stream()
                .filter(person -> person.getAddress().equals(adresse))
                .collect(Collectors.toList());
    }

    @Override
    public List<Firestation> getFirestations() {
        log.info("Get all firestations");
        return data.getFirestations();
    }

    @Override
    public List<Firestation> getFirestationsByNumber(Integer station) {
        log.info("Get all firestations by number");

        return getFirestations()
                .stream()
                .filter(firestation -> firestation.getStation() == station)
                .collect(Collectors.toList());
    }

    @Override
    public List<Firestation> getFirestationsByAddress(String address) {
        log.info("Get all firestations by address");

        return getFirestations()
                .stream()
                .filter(firestation -> firestation.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    @Override
    public List<MedicalRecord> getMedicalrecords() {
        log.info("Get all medicalrecords");
        return data.getMedicalRecords();
    }

    @Override
    public Optional<MedicalRecord> getMedicalRecordById(Id id) {
        log.info("Get all medicalrecords by Id");
        return getMedicalrecords()
                .stream()
                .filter(medicalrecord -> medicalrecord.getId().equals(id))
                .findFirst();
    }

    @Override
    public Data getData() {
        return null;
    }
}
