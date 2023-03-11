package com.example.safetynetalerts.dto;

import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

/**
 * The type Families by station dto.
 */
public class FamiliesByStationDto {
	private final String lastName;
	private final String firstName;
	private final String phone;
	private final Integer age;
	private final List<String> medications;
	private final List<String> allergies;


	/**
	 * Instantiates a new Families by station dto.
	 *
	 * @param person        the person
	 * @param medicalRecord the medical record
	 */
	public FamiliesByStationDto(Personne person, MedicalRecord medicalRecord) {
		this.lastName = person.getLastName();
		this.firstName = person.getFirstName();
		this.phone = person.getPhone();
		this.age = medicalRecord.getAge();
		this.medications = medicalRecord.getMedications();
		this.allergies = medicalRecord.getAllergies();
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
	 * Gets first name.
	 *
	 * @return the first name
	 */
	@JsonGetter("firstName")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets phone.
	 *
	 * @return the phone
	 */
	@JsonGetter("phone")
	public String getPhone() {
		return phone;
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

}
