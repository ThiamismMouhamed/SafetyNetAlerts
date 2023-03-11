package com.example.safetynetalerts.service.firestation;

import com.example.safetynetalerts.model.Firestation;

public interface FirestationIn {

    void addMedicalRecord(Firestation firestation);
    void updateFirestation(Firestation firestation);
    void deleteFirestation(Firestation firestation);

}
