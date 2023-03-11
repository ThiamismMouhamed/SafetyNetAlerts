package com.example.safetynetalerts.dto;

import com.example.safetynetalerts.model.Id;
import com.example.safetynetalerts.model.Personne;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type Persons by station dto.
 */
public class PersonsByStationDto {

	private final String firstName;
	private final String lastName;
	private final String address;
	private final String phone;
	private final Id id;

	/**
	 * Instantiates a new Persons by station dto.
	 *
	 * @param person the person
	 */
	public PersonsByStationDto(Personne person) {

		this.firstName= person.getFirstName();
		this.lastName=person.getLastName();
		this.address = person.getAddress();
		this.phone = person.getPhone();
		this.id = person.getId();
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
	 * Gets phone.
	 *
	 * @return the phone
	 */
	@JsonGetter("phone")
	public String getPhone() {
		return phone;
	}

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	@JsonIgnore
	@JsonGetter("id")
	public Id getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		PersonsByStationDto that = (PersonsByStationDto) o;

		if(!firstName.equals(that.firstName)) return false;
		if(!lastName.equals(that.lastName)) return false;
		if(!address.equals(that.address)) return false;
		if(!phone.equals(that.phone)) return false;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		int result = firstName.hashCode();
		result = 31 * result + lastName.hashCode();
		result = 31 * result + address.hashCode();
		result = 31 * result + phone.hashCode();
		result = 31 * result + id.hashCode();
		return result;
	}
}
