package com.example.safetynetalerts.service.personne;

import com.example.safetynetalerts.model.Personne;

import java.util.Set;

public interface PersonneIn {

    void addPersonne(Personne personne);
    void updatePersonne(Personne personne);
    void deletePersonne(Personne personne);

    Set<String> getAllMailsByCity(String city);

    
}
