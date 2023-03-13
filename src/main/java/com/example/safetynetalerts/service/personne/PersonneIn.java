package com.example.safetynetalerts.service.personne;

import com.example.safetynetalerts.dto.ChildListAndFamilyListDto;
import com.example.safetynetalerts.dto.FamiliesByStationDto;
import com.example.safetynetalerts.dto.PersonByFirstNameAndLastNameDto;
import com.example.safetynetalerts.model.Personne;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PersonneIn {

    void addPersonne(Personne personne);
    void updatePersonne(Personne personne);
    void deletePersonne(Personne personne);

    Set<String> getAllMailsByCity(String city);
    List<PersonByFirstNameAndLastNameDto> getPersonsByFirstNameAndLastName(String firstName, String lastName);

    /**
     * This method using firestation number return persons by addresses
     *
     * @param stations define the number of the firestation, one or more stations can be used
     * @return This method return a map of persons by address for flood alert return by address list of persons with their firstName, lastName, phone, age, list of medications and list of allergies
     */
    Map<String, List<FamiliesByStationDto>> getPersonsByAddressStationForFloodAlert(List<Integer> stations);

    /**
     * Method used to get all children by address and their family, and the number of child and number of adults
     *
     * @param address used to filter persons
     * @return a list of child with firstName, lastName, phone and age and a list of Adults with same description
     */
    ChildListAndFamilyListDto getChildrenByAddress(String address);

    
}
