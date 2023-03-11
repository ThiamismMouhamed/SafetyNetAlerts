package com.example.safetynetalerts.service.dataStorage;

import com.example.safetynetalerts.model.*;

import java.util.List;
import java.util.Optional;

public interface DataStorageIn {
    List<Personne> getPersonnes();
    Optional<Personne> getPersonneById(Id id);
    List<Personne> getPersonneByAdresse(String adresse);
    List<Firestation> getFirestations();

    List<Firestation> getFirestationsByNumber(Integer station);

    List<Firestation> getFirestationsByAddress(String address);

    List<MedicalRecord> getMedicalrecords();
    Optional<MedicalRecord> getMedicalRecordById(Id id);
    Data getData();

}
