package com.example.safetynetalerts.service.firestation;

import com.example.safetynetalerts.exeptions.BadRequestExeptions;
import com.example.safetynetalerts.model.Firestation;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.medicalrecord.MedicalRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@Log4j2
public class FirestationService implements FirestationIn{
    private final DataStorageService dataStorageService;
    private final MedicalRecordService medicalRecordService;

    public FirestationService(DataStorageService dataStorageService, MedicalRecordService medicalRecordService){
        this.dataStorageService = dataStorageService;
        this.medicalRecordService = medicalRecordService;
    }


    @Override
    public void addMedicalRecord(Firestation firestation) {
        log.debug("Add a firestation: " + firestation);

        Optional<Firestation> optionalFirestation =
                dataStorageService
                        .getFirestations()
                        .stream()
                        .filter(f -> f.getAddress().equals(firestation.getAddress()))
                        .findFirst();

        if(optionalFirestation.isPresent()) {
            log.error("Error creating a new firestation");
            throw new BadRequestExeptions("Firestation serve already this address");
        }

        dataStorageService
                .getFirestations()
                .add(firestation);
        log.info("Firestation has benn created");

    }

    @Override
    public void updateFirestation(Firestation firestation) {
        log.debug("Update a firestation: " + firestation);

        Optional<Firestation> optionalFirestation =
                dataStorageService
                        .getFirestations()
                        .stream()
                        .filter(f -> f.getAddress().equals(firestation.getAddress()))
                        .findFirst();

        if(optionalFirestation.isPresent()) {
            int indexOfFirestation = dataStorageService.getFirestations().indexOf(optionalFirestation.get());
            dataStorageService
                    .getFirestations()
                    .set(indexOfFirestation, firestation);
            log.info("Firestation has been updated");
        } else {
            log.error("Error updating a firestation");
            throw new BadRequestExeptions("Firestation who serve this address doesn't exist");
        }

    }

    @Override
    public void deleteFirestation(Firestation firestation) {
        log.debug("Delete a firestation: " + firestation);

        Optional<Firestation> optionalFirestation =
                dataStorageService
                        .getFirestations()
                        .stream()
                        .filter(f -> f.getAddress().equals(firestation.getAddress()))
                        .findFirst();

        if(optionalFirestation.isPresent()) {
            dataStorageService
                    .getFirestations()
                    .remove(optionalFirestation.get());
            log.info("Firestation has benn removed");
        } else {
            log.error("Error deleting a firestation");
            throw new BadRequestExeptions("Firestation who serve this address doesn't exist");
        }

    }
}
