package com.example.safetynetalerts.service.medicalrecord;

import com.example.safetynetalerts.model.Id;
import com.example.safetynetalerts.model.MedicalRecord;

import java.util.Optional;

public interface MedicalRecordIn {

    void addMedicalRecord(MedicalRecord medicalRecord) ;
    void updateMedicalRecord(MedicalRecord medicalRecord);
    void deleteMedicalRecord(String firsName, String lastName);
    Optional<MedicalRecord> getMedicalRecordByPersonneId(Id id);
}
