package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.service.firestation.FirestationService;
import com.example.safetynetalerts.service.personne.PersonneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AlertController {

    private final FirestationService firestationService;
    private final PersonneService personneService;

    public AlertController(FirestationService firestationService, PersonneService personneService) {
        this.firestationService = firestationService;
        this.personneService = personneService;
    }


}
