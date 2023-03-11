package com.example.safetynetalerts.dto;

import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

/**
 * The type Person by first name and last name dto.
 */
public class PersonByFirstNameAndLastNameDto {

	private final String firstName;
	private final String lastName;
	private final String email;
	private Integer age;
	private List<String> medications;
	private List<String> allergies;

	/**
	 * Instantiates a new Person by first name and last name dto.
	 *
	 * @param person        the person
	 * @param medicalRecord the medical record
	 */
	public PersonByFirstNameAndLastNameDto(Personne person, MedicalRecord medicalRecord) {

		this.firstName= person.getFirstName();
		this.lastName=person.getLastName();
		this.email= person.getEmail();
		this.age=medicalRecord.getAge();
		this.medications=medicalRecord.getMedications();
		this.allergies=medicalRecord.getAllergies();
	}

	/**
	 * Instantiates a new Person by first name and last name dto.
	 *
	 * @param person the person
	 */
	public PersonByFirstNameAndLastNameDto(Personne person) {
		this.firstName= person.getFirstName();
		this.lastName=person.getLastName();
		this.email= person.getEmail();
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
	 * Gets email.
	 *
	 * @return the email
	 */
	@JsonGetter("email")
	public String getEmail() {
		return email;
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

		PersonByFirstNameAndLastNameDto that = (PersonByFirstNameAndLastNameDto) o;

		if(!firstName.equals(that.firstName)) return false;
		if(!lastName.equals(that.lastName)) return false;
		if(!email.equals(that.email)) return false;
		if(!age.equals(that.age)) return false;
		if(!medications.equals(that.medications)) return false;
		return allergies.equals(that.allergies);
	}

	@Override
	public int hashCode() {
		int result = firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + email.hashCode();
		result = 31 * result + age.hashCode();
		result = 31 * result + medications.hashCode();
		result = 31 * result + allergies.hashCode();
		return result;
	}
}
