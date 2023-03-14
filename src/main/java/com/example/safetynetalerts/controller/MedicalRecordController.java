package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.service.medicalrecord.MedicalRecordService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
            * Add medical record response entity.
            *
            * @param medicalRecord the medical record
	 * @return the response entity
	 */
    @PostMapping("/medicalRecord")
    public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.debug("Add a medical record");
        medicalRecordService.addMedicalRecord(medicalRecord);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Update medical record response entity.
     *
     * @param medicalRecord the medical record
     * @return the response entity
     */
    @PutMapping("/medicalRecord")
    public ResponseEntity<Void> updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        log.debug("Update a medical record");
        medicalRecordService.updateMedicalRecord(medicalRecord);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete medical record response entity.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @return the response entity
     */
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam String firstName, @RequestParam String lastName) {
        log.debug("Delete a medical record");
        medicalRecordService.deleteMedicalRecord(firstName, lastName);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
