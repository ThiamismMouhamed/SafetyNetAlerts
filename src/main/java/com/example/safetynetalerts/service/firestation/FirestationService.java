package com.example.safetynetalerts.service.firestation;

import com.example.safetynetalerts.dto.NumberOfAdultsAndChildrenDto;
import com.example.safetynetalerts.dto.PersonsByAddressDto;
import com.example.safetynetalerts.dto.PersonsByStationDto;
import com.example.safetynetalerts.exeptions.BadRequestExeptions;
import com.example.safetynetalerts.model.Firestation;
import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.medicalrecord.MedicalRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

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
    public void addFirestation(Firestation firestation) {
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

    @Override
    public List<PersonsByStationDto> getPersonsByStation(int stationNumber) {
        log.debug("Get persons by firestation number: " + stationNumber);

        log.info("Get firestation address by station number");
        List<String> firestationAddressByStationNumber =
                dataStorageService
                        .getFirestationsByNumber(stationNumber)
                        .stream()
                        .map(Firestation::getAddress)
                        .collect(Collectors.toList());


        log.info("Get all persons by address");

        return dataStorageService
                .getPersonnes()
                .stream()
                .filter(person -> firestationAddressByStationNumber.contains(person.getAddress()))
                .map(PersonsByStationDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getPhoneByFirestationNumber(int station) {
        log.debug("Get phone by firestation number" + station);

        List<String> firestationAddress =
                dataStorageService
                        .getFirestationsByNumber(station)
                        .stream()
                        .map(Firestation::getAddress)
                        .collect(Collectors.toList());
        log.info("Phone number by firestation number has been recovered");
        return dataStorageService
                .getPersonnes()
                .stream()
                .filter(p -> firestationAddress.contains(p.getAddress()))
                .map(Personne::getPhone)
                .collect(Collectors.toSet());
    }

    @Override
    public List<PersonsByAddressDto> getPersonsByAddress(String address) {
        log.debug("Get persons by address" + address);

        List<Personne> persons = dataStorageService.getPersonneByAdresse(address);

        List<MedicalRecord> medicalrecords = dataStorageService.getMedicalrecords();

        List<Firestation> fireStations =
                dataStorageService
                        .getFirestationsByAddress(address);

        List<PersonsByAddressDto> personsByAddressDto = new ArrayList<>();

        for(Personne person : persons) {
            List<PersonsByAddressDto> aggregate =
                    fireStations
                            .stream()
                            .map(fireStation -> {
                                try {
                                    return new PersonsByAddressDto(person, fireStation, Objects.requireNonNull(medicalrecords
                                            .stream()
                                            .filter(medicalRecord -> medicalRecord.getId().equals(person.getId()))
                                            .findFirst().orElse(null)));
                                } catch(Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            })
                            .collect(Collectors.toList());

            personsByAddressDto.addAll(aggregate);
            log.info("All persons by address has been recovered");
        }

        return personsByAddressDto;
    }

    @Override
    public NumberOfAdultsAndChildrenDto getNumbersOfChildrenAndAdultsByStation(int station) {
        log.debug("Get numbers of adults and children by station");
        int adultsNumber = 0;
        int childrenNumber = 0;

        for(PersonsByStationDto person : getPersonsByStation(station)) {

            Optional<MedicalRecord> medicalrecord = medicalRecordService.getMedicalRecordByPersonneId(person.getId());

            if(medicalrecord.isPresent()) {
                Integer personAge = medicalrecord.get().getAge();
                if(personAge > 18) {
                    adultsNumber++;
                } else childrenNumber++;
            }
        }
        log.info("Return number of children and adults");
        return new NumberOfAdultsAndChildrenDto(childrenNumber, adultsNumber);
    }
}
