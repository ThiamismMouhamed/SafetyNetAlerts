package com.example.safetynetalerts.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

/**
 * The type Child list and family list dto.
 */
public class ChildListAndFamilyListDto {
	
	private List<ChildrenByAddressDto> childByAddress;
	private List<AdultsByAddressDto> adultsByAddress;

	/**
	 * Instantiates a new Child list and family list dto.
	 *
	 * @param childrenByAddressDto the children by address dto
	 * @param adultsByAddressDto   the adults by address dto
	 */
	public ChildListAndFamilyListDto(List<ChildrenByAddressDto> childrenByAddressDto, List<AdultsByAddressDto> adultsByAddressDto) {
		this.childByAddress = childrenByAddressDto;
		this.adultsByAddress = adultsByAddressDto;
	}

	/**
	 * Gets get children by address dto.
	 *
	 * @return the get children by address dto
	 */
	@JsonGetter("child")
	public List<ChildrenByAddressDto> getGetChildrenByAddressDto() {
		return childByAddress;
	}

	/**
	 * Gets get adults by address dto.
	 *
	 * @return the get adults by address dto
	 */
	@JsonGetter("adults")
	public List<AdultsByAddressDto> getGetAdultsByAddressDto() {
		return adultsByAddress;
	}

	/**
	 * Sets get children by address dto.
	 *
	 * @param childrenByAddressDto the children by address dto
	 */
	public void setGetChildrenByAddressDto(List<ChildrenByAddressDto> childrenByAddressDto) {
		this.childByAddress = childrenByAddressDto;
	}

	/**
	 * Sets get adults by address dto.
	 *
	 * @param adultsByAddressDto the adults by address dto
	 */
	public void setGetAdultsByAddressDto(List<AdultsByAddressDto> adultsByAddressDto) {
		this.adultsByAddress = adultsByAddressDto;
	}
}
