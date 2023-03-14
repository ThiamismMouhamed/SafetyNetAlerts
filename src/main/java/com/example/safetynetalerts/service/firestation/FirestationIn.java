package com.example.safetynetalerts.service.firestation;

import com.example.safetynetalerts.dto.NumberOfAdultsAndChildrenDto;
import com.example.safetynetalerts.dto.PersonsByAddressDto;
import com.example.safetynetalerts.dto.PersonsByStationDto;
import com.example.safetynetalerts.model.Firestation;

import java.util.List;
import java.util.Set;

public interface FirestationIn {

    void addFirestation(Firestation firestation);
    void updateFirestation(Firestation firestation);
    void deleteFirestation(Firestation firestation);
    List<PersonsByStationDto> getPersonsByStation(int stationNumber);

    /**
     * Method used to return phone number
     *
     * @param station is the station number
     * @return a list of phone number without duplicate serve by this station
     */
    Set<String> getPhoneByFirestationNumber(int station);

    /**
     * Used to return persons by address
     *
     * @param address is the address served by the fire station
     * @return a list of persons served at this address return firstName, lastName, age, list of medications, list of allergies and fire station number
     */
    List<PersonsByAddressDto> getPersonsByAddress(String address);

    /**
     * Return number of adults and children
     *
     * @param station is the number of the fire station
     * @return the number of adults and child served by the fire station by station in parameters
     */
    NumberOfAdultsAndChildrenDto getNumbersOfChildrenAndAdultsByStation(int station);

}
