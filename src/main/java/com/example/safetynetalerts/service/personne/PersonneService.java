package com.example.safetynetalerts.service.personne;

import com.example.safetynetalerts.exeptions.BadRequestExeptions;
import com.example.safetynetalerts.model.Personne;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
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
}
