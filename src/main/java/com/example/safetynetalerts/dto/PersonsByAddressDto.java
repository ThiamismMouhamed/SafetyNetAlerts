package com.example.safetynetalerts.dto;

import com.example.safetynetalerts.model.Firestation;
import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * The type Persons by address dto.
 */
public class PersonsByAddressDto {
	
	private final String firstName;
	private final String lastName;
	private final String address;
	private final Integer age;
	private final List<String> medications;
	private final List<String> allergies;
	private final int station;

	/**
	 * Instantiates a new Persons by address dto.
	 *
	 * @param person        the person
	 * @param fireStation   the fire station
	 * @param medicalRecord the medical record
	 */
	public PersonsByAddressDto(Personne person, Firestation fireStation, MedicalRecord medicalRecord) {
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.address = person.getAddress();
		this.station = fireStation.getStation();
		this.age = medicalRecord.getAge();
		this.medications = medicalRecord.getMedications();
		this.allergies = medicalRecord.getAllergies();
	}

	/**
	 * Gets first name.
	 *
	 * @return the first name
	 */
	@JsonGetter("firstName")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets last name.
	 *
	 * @return the last name
	 */
	@JsonGetter("lastName")
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets address.
	 *
	 * @return the address
	 */
	@JsonGetter("address")
	@JsonIgnore
	public String getAddress() {
		return address;
	}

	/**
	 * Gets medications.
	 *
	 * @return the medications
	 */
	@JsonGetter("medications")
	public List<String> getMedications() {
		return medications;
	}

	/**
	 * Gets allergies.
	 *
	 * @return the allergies
	 */
	@JsonGetter("allergies")
	public List<String> getAllergies() {
		return allergies;
	}

	/**
	 * Gets station.
	 *
	 * @return the station
	 */
	@JsonGetter("station")
	public int getStation() {
		return station;
	}

	/**
	 * Gets age.
	 *
	 * @return the age
	 */
	@JsonGetter("age")
	public Integer getAge() {
		return age;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		PersonsByAddressDto that = (PersonsByAddressDto) o;

		if(station != that.station) return false;
		if(!firstName.equals(that.firstName)) return false;
		if(!lastName.equals(that.lastName)) return false;
		if(!address.equals(that.address)) return false;
		if(!age.equals(that.age)) return false;
		if(!medications.equals(that.medications)) return false;
		return allergies.equals(that.allergies);
	}

	@Override
	public int hashCode() {
		int result = firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + address.hashCode();
		result = 31 * result + age.hashCode();
		result = 31 * result + medications.hashCode();
		result = 31 * result + allergies.hashCode();
		result = 31 * result + station;
		return result;
	}
}
