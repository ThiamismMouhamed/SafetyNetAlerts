package com.example.safetynetalerts.controller;


import com.example.safetynetalerts.dto.PersonByFirstNameAndLastNameDto;
import com.example.safetynetalerts.model.Personne;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.personne.PersonneService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
public class PersonneController {

    private final PersonneService personneService;
    private final DataStorageService dataStorageService;

    public PersonneController(PersonneService personneService, DataStorageService dataStorageService) {
        this.personneService = personneService;
        this.dataStorageService = dataStorageService;
    }

    /**
     * Person list list.
     *
     * @return the list
     */
    @GetMapping("/person")
    public List<Personne> personList() {
        return dataStorageService.getPersonnes();
    }


    /**
     * Add person response entity.
     *
     * @param person the person
     * @return the response entity
     */
    @PostMapping("/person")
    public ResponseEntity<Void> addPerson(@RequestBody Personne person) {
        log.debug("Add a new person");
        personneService.addPersonne(person);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update person response entity.
     *
     * @param person the person
     * @return the response entity
     */
    @PutMapping("/person")
    public ResponseEntity<Void> updatePerson(@RequestBody Personne person) {
        log.debug("Update a person");
        personneService.updatePersonne(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete person response entity.
     *
     * @param person the person
     * @return the response entity
     */
    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(@RequestBody Personne person) {
        log.debug("Delete a person");
        personneService.deletePersonne(person);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets persons by first name and last name.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the persons by first name and last name
     */
    @GetMapping("/personInfo")
    ResponseEntity<List<PersonByFirstNameAndLastNameDto>> getPersonsByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        log.debug("Get persons by firstname and lastname");
        return new ResponseEntity<>(personneService.getPersonsByFirstNameAndLastName(firstName, lastName), HttpStatus.OK);
    }
}
