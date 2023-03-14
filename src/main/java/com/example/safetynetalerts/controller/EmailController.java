package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.service.personne.PersonneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class EmailController {

    private final PersonneService personneService;

    /**
     * Instantiates a new Community email controller.
     *
     * @param personneService the person manager
     */
    public EmailController(PersonneService personneService) {
        this.personneService = personneService;
    }


    /**
     * Gets all mails by city.
     *
     * @param city the city
     * @return the all mails by city
     */
    @GetMapping("/communityEmail")
    ResponseEntity<Set<String>> getAllMailsByCity(@RequestParam String city) {
        return new ResponseEntity<>(personneService.getAllMailsByCity(city), HttpStatus.OK);
    }
}
