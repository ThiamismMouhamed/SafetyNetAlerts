package com.example.safetynetalerts.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class PersonsByStationAndAdultsNumberAndChildrenNumberDto {
	private final int stationNumber;
	private List<PersonsByStationDto> personsByStation;
	private int numberOfAdult;
	private int numberOfChildren;

	public PersonsByStationAndAdultsNumberAndChildrenNumberDto(int stationNumber, List<PersonsByStationDto>personsByStation, int numberOfAdult, int numberOfChildren) {
		this.stationNumber = stationNumber;
		this.personsByStation = personsByStation;
		this.numberOfAdult = numberOfAdult;
		this.numberOfChildren = numberOfChildren;
	}

	@JsonGetter
	public int getStationNumber() {
		return stationNumber;
	}
	@JsonGetter
	public List<PersonsByStationDto> getPersonsByStation() {
		return personsByStation;
	}
	@JsonSetter
	public void setPersonsByStation(List<PersonsByStationDto> personsByStation) {
		this.personsByStation = personsByStation;
	}
	@JsonGetter
	public int getNumberOfAdult() {
		return numberOfAdult;
	}
	@JsonSetter
	public void setNumberOfAdult(int numberOfAdult) {
		this.numberOfAdult = numberOfAdult;
	}
	@JsonGetter
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
	@JsonSetter
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
}
