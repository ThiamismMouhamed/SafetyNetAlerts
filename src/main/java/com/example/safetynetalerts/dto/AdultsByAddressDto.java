package com.example.safetynetalerts.dto;

import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type Adults by address dto.
 */
public class AdultsByAddressDto {
	
	private final String firstName;
	private final String lastName;
	private final String address;
	private final String city;
	private final Integer zip;
	private final String phone;
	private final String email;
	private final Integer age;

	/**
	 * Instantiates a new Adults by address dto.
	 *
	 * @param person        the person
	 * @param medicalrecord the medicalrecord
	 */
	public AdultsByAddressDto(Personne person, MedicalRecord medicalrecord) {
		this.firstName = person.getFirstName();
		this.lastName = person.getLastName();
		this.address = person.getAddress();
		this.city = person.getCity();
		this.zip = person.getZip();
		this.phone = person.getPhone();
		this.email = person.getEmail();
		this.age = medicalrecord.getAge();
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
	public String getAddress() {
		return address;
	}

	/**
	 * Gets city.
	 *
	 * @return the city
	 */
	@JsonGetter("city")
	public String getCity() {
		return city;
	}

	/**
	 * Gets zip.
	 *
	 * @return the zip
	 */
	@JsonGetter("zip")
	public Integer getZip() {
		return zip;
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
	 * Gets email.
	 *
	 * @return the email
	 */
	@JsonGetter("email")
	public String getEmail() {
		return email;
	}

	/**
	 * Gets age.
	 *
	 * @return the age
	 */
	@JsonGetter("age")
	@JsonIgnore
	public Integer getAge() {
		return age;
	}

	/**
	 * S major boolean.
	 *
	 * @return the boolean
	 */
	@JsonGetter("isMinor")
	@JsonIgnore
	public boolean iSMajor() {
		return age > 18;
	}
	
}
