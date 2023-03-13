package com.example.safetynetalerts.service.personne;

import com.example.safetynetalerts.dto.*;
import com.example.safetynetalerts.exeptions.BadRequestExeptions;
import com.example.safetynetalerts.model.Firestation;
import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Log4j2
public class PersonneService implements PersonneIn{

    private DataStorageService dataStorageService;
    /**
     * Instantiates a new Person manager.
     *
     * @param dataStorageService the data storage
     */
    @Autowired
    public PersonneService(DataStorageService dataStorageService) {
        this.dataStorageService = dataStorageService;
    }

    @Override
    public void addPersonne(Personne personne) {
        log.debug("Add a person: " + personne.getLastName() + " " + personne.getFirstName());

        Optional<Personne> optionalPerson =
                dataStorageService.getPersonneById(personne.getId());

        if(optionalPerson.isPresent()) {
            log.error("Impossible to create this person, this person exist already");
            throw new BadRequestExeptions("This person exist already");
        }

        dataStorageService
                .getPersonnes()
                .add(personne);
        log.info("person has been added");

    }

    @Override
    public void updatePersonne(Personne personne) {
        log.debug("Update a person: " + personne.getLastName() + " " + personne.getFirstName());

        Optional<Personne> optionalPerson =
                dataStorageService
                        .getPersonneById(personne.getId());

        if(optionalPerson.isPresent()) {
            int indexOfPerson = dataStorageService.getPersonnes().indexOf(optionalPerson.get());

            dataStorageService
                    .getPersonnes()
                    .set(indexOfPerson, personne);
            log.info("person has been updated");

        } else {
            log.error("Impossible to update this person, this person doesn't exist");
            throw new BadRequestExeptions("This person doesn't exist");

        }

    }

    @Override
    public void deletePersonne(Personne personne) {
        log.debug("Delete a person: " + personne.getLastName() + " " + personne.getFirstName());

        Optional<Personne> optionalPerson =
                dataStorageService
                        .getPersonneById(personne.getId());

        if(optionalPerson.isPresent()) {

            dataStorageService
                    .getPersonnes()
                    .remove(optionalPerson.get());

            log.info("person has been removed");

        } else {
            log.error("Impossible to delete this person, this person doesn't exist");
            throw new BadRequestExeptions("This person doesn't exist");
        }

    }

    @Override
    public Set<String> getAllMailsByCity(String city) {
        log.debug("Get all mails in: " + city);
        return dataStorageService
                .getPersonnes()
                .stream()
                .filter(p -> city.equals(p.getCity()))
                .map(Personne::getEmail)
                .collect(Collectors.toSet());
    }

    @Override
    public List<PersonByFirstNameAndLastNameDto> getPersonsByFirstNameAndLastName(String firstName, String lastName) {
        log.debug("Get all persons by address: " + firstName + " " + lastName);

        return dataStorageService.getPersonnes()
                .stream()
                .filter(p -> lastName.equals(p.getLastName()))
                .map(person -> dataStorageService.getMedicalrecords()
                        .stream()
                        .filter(medicalrecord -> medicalrecord.getId().equals(person.getId()))
                        .map(medicalrecord -> new PersonByFirstNameAndLastNameDto(person, medicalrecord))
                        .findFirst().orElse(null))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<FamiliesByStationDto>> getPersonsByAddressStationForFloodAlert(List<Integer> stations) {
        log.debug("Get all persons by address: " + stations);

        List<MedicalRecord> medicalrecords = dataStorageService.getMedicalrecords();

        List<String> allAddressesByStationNumber = new ArrayList<>();
        Map<String, List<FamiliesByStationDto>> allPersons = new HashMap<>();

        for(Integer firestationNumber : stations) {
            List<String> getAllAddressesByStationNumber =
                    dataStorageService
                            .getFirestations()
                            .stream()
                            .filter(firestation -> firestation.getStation() == firestationNumber)
                            .map(Firestation::getAddress)
                            .collect(Collectors.toList());
            allAddressesByStationNumber.addAll(getAllAddressesByStationNumber);
            log.info("get all addresses by station number");
        }

        for(String address : allAddressesByStationNumber) {
            List<FamiliesByStationDto> getAllPersonsWithThisAddress =
                    dataStorageService
                            .getPersonnes()
                            .stream()
                            .filter(person -> person.getAddress().equals(address))
                            .map(person -> {
                                        MedicalRecord m = medicalrecords
                                                .stream()
                                                .filter(medicalRecord -> medicalRecord.getId().equals(person.getId()))
                                                .findFirst()
                                                .orElseThrow(() -> new BadRequestExeptions(""));
                                        return new FamiliesByStationDto(person, m);
                                    }
                            )
                            .collect(Collectors.toList());
            allPersons.put(address, getAllPersonsWithThisAddress);
            log.info("Get all persons by address");
        }

        return allPersons;
    }

    @Override
    public ChildListAndFamilyListDto getChildrenByAddress(String address) {
        log.debug("Get children by address: " + address);

        List<MedicalRecord> medicalrecords = dataStorageService.getMedicalrecords();

        List<ChildrenByAddressDto> getChild = new ArrayList<>();
        List<AdultsByAddressDto> getAdults = new ArrayList<>();
        ChildListAndFamilyListDto childListAndFamilyListDto = new ChildListAndFamilyListDto(getChild, getAdults);

        for(MedicalRecord medicalRecord : medicalrecords) {
            List<ChildrenByAddressDto> getChildrenByAddress =
                    dataStorageService
                            .getPersonnes()
                            .stream()
                            .filter(person -> person.getId().equals(medicalRecord.getId()))
                            .filter(person -> person.getAddress().equals(address))
                            .map(person -> new ChildrenByAddressDto(person, medicalRecord))
                            .filter(ChildrenByAddressDto::isMinor)
                            .collect(Collectors.toList());

            getChild.addAll(getChildrenByAddress);
            log.info("Get child by address");
        }

        for(MedicalRecord medicalrecord : medicalrecords) {
            if(!getChild.isEmpty()) {
                List<AdultsByAddressDto> getAdultByAddress =
                        dataStorageService
                                .getPersonnes()
                                .stream()
                                .filter(person -> person.getId().equals(medicalrecord.getId()))
                                .filter(person -> person.getAddress().equals(address))
                                .map(person -> new AdultsByAddressDto(person, medicalrecord))
                                .filter(AdultsByAddressDto::iSMajor)
                                .collect(Collectors.toList());

                getAdults.addAll(getAdultByAddress);
                log.info("Get adults by address");
            }
        }

        childListAndFamilyListDto.setGetChildrenByAddressDto(getChild);
        childListAndFamilyListDto.setGetAdultsByAddressDto(getAdults);
        log.info("List of child by address and adult by address");
        return childListAndFamilyListDto;
    }

}
