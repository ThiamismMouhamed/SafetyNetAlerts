package com.example.safetynetalerts.service.integration;

import com.example.safetynetalerts.dto.ChildListAndFamilyListDto;
import com.example.safetynetalerts.dto.FamiliesByStationDto;
import com.example.safetynetalerts.dto.PersonByFirstNameAndLastNameDto;
import com.example.safetynetalerts.exeptions.BadRequestExeptions;
import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.personne.PersonneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertThrows;

@SpringBootTest()
class PersonIT {

	@Autowired
	private DataStorageService dataStorage;

	@Autowired
	private PersonneService personManager;

	Personne person;

	@BeforeEach
	void setUp() throws IOException {
		dataStorage = new DataStorageService();
		personManager = new PersonneService(dataStorage);
	}

	@Test
	void shouldAddAPerson() {
		//GIVEN
		person = new Personne("test", "Test", "33 rue Pommier", "Paris", 75013, "0134434543", "jeremy@mail.com");

		//WHEN
		personManager.addPersonne(person);
		boolean personCreated = dataStorage.getPersonnes().contains(person);

		//THEN
		Assertions.assertTrue(personCreated);

	}

	@Test
	void shouldReturnBadRequestExceptionsWhenAddAPersonAlreadyExisting() {
		//GIVEN
		person = new Personne("John", "Boyd", "33 rue Pommier", "Paris", 75013, "0134434543", "jeremy@mail.com");

		//THEN
		assertThrows(BadRequestExeptions.class, () -> personManager.addPersonne(person));

	}

	@Test
	void shouldUpdateAPersonWhenPersonAlreadyExisting() {
		personManager.updatePersonne(new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));

		Assertions.assertTrue(dataStorage.getPersonnes()
				.stream()
				.filter(p -> p.getFirstName().equals("John") && p.getLastName().equals("Boyd"))
				.anyMatch(p -> p.getPhone().equals("841-874-0000")));


	}

	@Test
	void shouldReturnBadRequestExceptionsWhenUpdateANotExistentPerson() {

		person = new Personne("Jeremy", "Boyd", "33 rue Pommier", "Paris", 75013, "0134434543", "jeremy@mail.com");
		Assertions.assertThrows(BadRequestExeptions.class, () -> personManager.updatePersonne(person));

	}

	@Test
	void shouldDeleteWhenPersonIsAlreadyExisting() {
		personManager.deletePersonne(new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"));

		Assertions.assertTrue(dataStorage.getPersonnes()
				.stream()
				.noneMatch(p -> p.getFirstName().equals("John") && person.getLastName().equals("Boyd")));
	}

	@Test
	void shouldReturnBadRequestExceptionsWhenDeletingANotExistentPerson() {

		person = new Personne("Jeremy", "Boyd", "33 rue Pommier", "Paris", 75013, "0134434543", "jeremy@mail.com");
		assertThrows(BadRequestExeptions.class, () -> personManager.deletePersonne(person));

	}

	@Test
	void shouldReturnAMailExistingWithCulverCity() {
		//GIVEN
		String city = "Culver";
		String emailExpected = "aly@imail.com";
		int numberOfEmailExpected = 15;

		//WHEN
		Set<String> getAllMailByCity = personManager.getAllMailsByCity(city);

		//THEN
		Assertions.assertTrue(getAllMailByCity.contains(emailExpected));
		Assertions.assertEquals(numberOfEmailExpected, getAllMailByCity.size());
	}

	@Test
	void shouldReturnAnEmptyList() {
		//GIVEN
		String city = "";
		//WHEN
		Set<String> getAllMailByCity = personManager.getAllMailsByCity(city);

		//THEN
		Assertions.assertTrue(getAllMailByCity.isEmpty());
	}

	@Test
	void shouldReturnAnExistingPerson() {
		Personne personExpected = new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com");

		List<Personne> allPersons = dataStorage.getPersonnes();

		Assertions.assertNotNull(allPersons);
		Assertions.assertFalse(allPersons.isEmpty());
		Assertions.assertTrue(allPersons.contains(personExpected));
	}

	@Test
	void shouldReturnAPersonUsingFirstNameAndLastname() {
		//GIVEN
		PersonByFirstNameAndLastNameDto personExpected = new PersonByFirstNameAndLastNameDto(new Personne("Roger", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"), new MedicalRecord("Roger", "Boyd", "09/06/2017", new ArrayList<>(), new ArrayList<>()));

		//WHEN
		List<PersonByFirstNameAndLastNameDto> persons = personManager.getPersonsByFirstNameAndLastName(personExpected.getFirstName(), personExpected.getLastName());

		//THEN
		Assertions.assertTrue(persons.contains(personExpected));
	}

	@Test
	void shouldReturnAddressesForStationNumber1And3() {
		//GIVEN
		List<Integer> stations = new ArrayList<>();
		stations.add(1);
		stations.add(3);

		//WHEN
		Map<String, List<FamiliesByStationDto>> persons = personManager.getPersonsByAddressStationForFloodAlert(stations);

		//THEN
		Assertions.assertTrue(persons.containsKey("644 Gershwin Cir"));
		Assertions.assertTrue(persons.containsKey("748 Townings Dr"));
		Assertions.assertFalse(persons.containsKey("489 Manchester St"));
	}

	@Test
	void get2ChildrenByAddress1509CulverSt() {
		//GIVEN
		String address = "1509 Culver St";

		//WHEN
		ChildListAndFamilyListDto childListAndFamilyListDto = personManager.getChildrenByAddress(address);

		//THEN
		Assertions.assertEquals(2, childListAndFamilyListDto.getGetChildrenByAddressDto().size());
	}

	@Test
	void get3AdultsByAddress1509CulverSt() {
		//GIVEN
		String address = "1509 Culver St";

		//WHEN
		ChildListAndFamilyListDto childListAndFamilyListDto = personManager.getChildrenByAddress(address);

		//THEN
		Assertions.assertEquals(3, childListAndFamilyListDto.getGetAdultsByAddressDto().size());
	}

	@Test
	void get5MembersByAddress1509CulverSt() {
		//GIVEN
		String address = "1509 Culver St";

		//WHEN
		ChildListAndFamilyListDto childListAndFamilyListDto = personManager.getChildrenByAddress(address);

		//THEN
		Assertions.assertEquals(5, childListAndFamilyListDto.getGetAdultsByAddressDto().size() + childListAndFamilyListDto.getGetChildrenByAddressDto().size());
	}

}