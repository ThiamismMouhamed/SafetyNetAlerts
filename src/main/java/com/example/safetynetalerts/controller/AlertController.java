package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.dto.ChildListAndFamilyListDto;
import com.example.safetynetalerts.dto.FamiliesByStationDto;
import com.example.safetynetalerts.dto.PersonsByAddressDto;
import com.example.safetynetalerts.service.firestation.FirestationService;
import com.example.safetynetalerts.service.personne.PersonneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
public class AlertController {

    private final FirestationService firestationService;
    private final PersonneService personneService;

    public AlertController(FirestationService firestationService, PersonneService personneService) {
        this.firestationService = firestationService;
        this.personneService = personneService;
    }

    /**
     * Gets persons by address.
     *
     * @param address the address
     * @return the persons by address
     */
    @GetMapping("/fire")
    ResponseEntity<List<PersonsByAddressDto>> getPersonsByAddress(@RequestParam String address) {
        return new ResponseEntity<>(firestationService.getPersonsByAddress(address), HttpStatus.OK);
    }

    /**
     * Gets phone numbers by firestation number.
     *
     * @param firestation the firestation
     * @return the phone numbers by firestation number
     */
    @GetMapping("/phoneAlert")
    ResponseEntity<Set<String>> getPhoneNumbersByFirestationNumber(@RequestParam int firestation) {
        return new ResponseEntity<>(firestationService.getPhoneByFirestationNumber(firestation), HttpStatus.OK);
    }

    /**
     * Gets child by address.
     *
     * @param address the address
     * @return the child by address
     */
    @GetMapping("/childAlert")
    ResponseEntity<ChildListAndFamilyListDto> getChildByAddress(@RequestParam String address) {
        return new ResponseEntity<>(personneService.getChildrenByAddress(address), HttpStatus.OK);
    }

    /**
     * Gets persons by address station for flood alert.
     *
     * @param stations the stations
     * @return the persons by address station for flood alert
     */
    @GetMapping("/flood/stations/")
    ResponseEntity<Map<String, List<FamiliesByStationDto>>> getPersonsByAddressStationForFloodAlert(@RequestParam List<Integer> stations) {
        return new ResponseEntity<>(personneService.getPersonsByAddressStationForFloodAlert(stations), HttpStatus.OK);
    }


}
