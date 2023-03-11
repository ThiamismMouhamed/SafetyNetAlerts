package com.example.safetynetalerts.dto;

import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type Children by address dto.
 */
public class ChildrenByAddressDto {
	private final String firstName;
	private final String lastName;
	private final String phone;
	private final Integer age;

	/**
	 * Instantiates a new Children by address dto.
	 *
	 * @param person        the person
	 * @param medicalRecord the medical record
	 */
	public ChildrenByAddressDto(Personne person, MedicalRecord medicalRecord) {

		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.phone = person.getPhone();
		this.age=medicalRecord.getAge();
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
	 * Is minor boolean.
	 *
	 * @return the boolean
	 */
	@JsonGetter("isMinor")
	@JsonIgnore
	public boolean isMinor() {
		return age <= 18;
	}
	
}
