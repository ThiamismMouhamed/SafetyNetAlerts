package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.dto.NumberOfAdultsAndChildrenDto;
import com.example.safetynetalerts.dto.PersonsByStationAndAdultsNumberAndChildrenNumberDto;
import com.example.safetynetalerts.dto.PersonsByStationDto;
import com.example.safetynetalerts.model.Firestation;
import com.example.safetynetalerts.service.firestation.FirestationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    private FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    /**
     * Add firestation response entity.
     *
     * @param firestation the firestation
     * @return the response entity
     */
    @PostMapping("/firestation")
    public ResponseEntity<Void> addFirestation(@RequestBody Firestation firestation) {
        firestationService.addFirestation(firestation);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update firestation response entity.
     *
     * @param firestation the firestation
     * @return the response entity
     */
    @PutMapping("/firestation")
    public ResponseEntity<Void> updateFirestation(@RequestBody Firestation firestation) {
        firestationService.updateFirestation(firestation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete firestation response entity.
     *
     * @param firestation the firestation
     * @return the response entity
     */
    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFirestation(@RequestBody Firestation firestation) {
        firestationService.deleteFirestation(firestation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets persons by station and adults number and children number dto.
     *
     * @param stationNumber the station number
     * @return the persons by station and adults number and children number dto
     */
    @GetMapping("/firestation")
    ResponseEntity<PersonsByStationAndAdultsNumberAndChildrenNumberDto> getPersonsByStationAndAdultsNumberAndChildrenNumberDto(@RequestParam int stationNumber) {

        List<PersonsByStationDto> personsByStation = firestationService.getPersonsByStation(stationNumber);
        NumberOfAdultsAndChildrenDto numberOfAdultsAndChildrenDto = firestationService.getNumbersOfChildrenAndAdultsByStation(stationNumber);

        PersonsByStationAndAdultsNumberAndChildrenNumberDto result = new PersonsByStationAndAdultsNumberAndChildrenNumberDto(stationNumber, personsByStation, numberOfAdultsAndChildrenDto.getNumberAdults(), numberOfAdultsAndChildrenDto.getNumberChildren());

        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
