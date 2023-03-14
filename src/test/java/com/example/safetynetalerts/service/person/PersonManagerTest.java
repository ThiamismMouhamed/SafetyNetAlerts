package com.example.safetynetalerts.service.person;

import com.example.safetynetalerts.dto.ChildListAndFamilyListDto;
import com.example.safetynetalerts.dto.PersonByFirstNameAndLastNameDto;
import com.example.safetynetalerts.exeptions.*;
import com.example.safetynetalerts.model.Data;
import com.example.safetynetalerts.model.Firestation;
import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.personne.PersonneService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PersonneManagerTest {

	@Mock
	DataStorageService mockDataStorage;

	private PersonneService PersonneManager;

	@BeforeEach
	public void Init() {
		PersonneManager = new PersonneService(mockDataStorage);
	}

	@Test
	void addPersonne() {

		//GIVEN
		Data data = new Data();
		Assertions.assertTrue(data.getPersons().isEmpty());
		Assertions.assertNotNull(data.getPersons());
		Personne PersonneToAdd = new Personne("firstNameTest", "lastNameTest", "address", "city", 3, "1234", "mail");

		//WHEN
		when(mockDataStorage.getData()).thenReturn(data);
		when(mockDataStorage.getPersonnes()).thenReturn(data.getPersons());
		PersonneManager.addPersonne(PersonneToAdd);

		//THEN
		Assertions.assertFalse(data.getPersons().isEmpty());
		Assertions.assertTrue(data.getPersons().contains(PersonneToAdd));

	}

	@Test
	void addPersonneAlreadyExisting() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertTrue(datas.getPersons().isEmpty());
		Personne existingPersonne = new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com");

		//WHEN
		when(mockDataStorage.getPersonnes()).thenReturn(new ArrayList<>());
		when(mockDataStorage.getPersonneById(any())).thenReturn(Optional.of(existingPersonne));

		//THEN
		Personne PersonneToAdd = new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com");
		Assertions.assertThrows(BadRequestExeptions.class, () -> PersonneManager.addPersonne(PersonneToAdd));
	}

	@Test
	void updatePersonneTest() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertTrue(datas.getPersons().isEmpty());

		Personne existingPersonne = new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com");
		datas.getPersons().add(existingPersonne);

		Personne PersonneUpdatedDatas = new Personne(existingPersonne.getLastName(), existingPersonne.getFirstName(), "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com");
		PersonneUpdatedDatas.setEmail("testEmail");

		//WHEN
		when(mockDataStorage.getPersonnes()).thenReturn(datas.getPersons());
		when(mockDataStorage.getPersonneById(any())).thenReturn(Optional.of(existingPersonne));

		PersonneManager.updatePersonne(PersonneUpdatedDatas);

		//THEN
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertFalse(datas.getPersons().isEmpty());
		Assertions.assertEquals(1, datas.getPersons().size());

		Personne PersonneUpdated = datas.getPersons().get(0);
		Assertions.assertEquals(PersonneUpdatedDatas, PersonneUpdated);
		Assertions.assertEquals("testEmail", PersonneUpdated.getEmail());
	}

	@Test
	void updatePersonneNotExistingTest() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertTrue(datas.getPersons().isEmpty());

		Personne PersonneToUpdate = new Personne("firstName1", "lastName1", "address", "city", 3, "1234", "mail");

		//WHEN
		when(mockDataStorage.getPersonnes()).thenReturn(datas.getPersons());
		when(mockDataStorage.getPersonneById(any())).thenReturn(Optional.empty());

		//THEN
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertThrows(BadRequestExeptions.class, () -> PersonneManager.updatePersonne(PersonneToUpdate));

	}

	@Test
	void deletePersonneTest() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertTrue(datas.getPersons().isEmpty());
		List<Personne> existingPersonnes = new ArrayList<>();

		Personne existingPersonne = new Personne("firstNameTest", "lastNameTest", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com");
		PersonneManager.addPersonne(existingPersonne);

		//WHEN
		when(mockDataStorage.getPersonnes()).thenReturn(existingPersonnes);
		when(mockDataStorage.getPersonneById(any())).thenReturn(Optional.of(existingPersonne));

		//THEN
		PersonneManager.deletePersonne(existingPersonne);

		Assertions.assertEquals(0, datas.getPersons().size());
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertTrue(datas.getPersons().isEmpty());
	}

	@Test
	void deletePersonneNotExistingTest() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertTrue(datas.getPersons().isEmpty());

		//WHEN
		when(mockDataStorage.getPersonnes()).thenReturn(datas.getPersons());
		Personne PersonneToDelete = new Personne("test1", "test2", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com");

		//THEN
		Assertions.assertNotNull(datas.getPersons());
		Assertions.assertThrows(BadRequestExeptions.class, () -> PersonneManager.deletePersonne(PersonneToDelete));
	}

	@Test
	void getAllMailsByCity() {

		String city = "Culver";
		List<Personne> Personnes = new ArrayList<>();
		Personnes.add(new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"));
		String mailExpected = "jaboyd@email.com";

		when(mockDataStorage.getPersonnes()).thenReturn(Personnes);
		Set<String> allMails = PersonneManager.getAllMailsByCity(city);

		Assertions.assertTrue(allMails.contains(mailExpected));
		Assertions.assertEquals(1, allMails.size());
	}

	@Test
	void getPersonsByFirstNameAndLastName() {
		//GIVEN
		Data data = new Data();

		data.getPersons().add(new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("test", "test", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));

		data.getMedicalRecords().add(new MedicalRecord("John", "Boyd", "02/02/2000", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("Jacob", "Boyd", "02/02/1983", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("test", "test", "02/02/2000", new ArrayList<>(), new ArrayList<>()));

		//WHEN
		when(mockDataStorage.getData()).thenReturn(data);
		when(mockDataStorage.getPersonnes()).thenReturn(data.getPersons());
		when(mockDataStorage.getMedicalrecords()).thenReturn(data.getMedicalRecords());

		List<PersonByFirstNameAndLastNameDto> PersonneByFirstNameAndLastNameDto = PersonneManager.getPersonsByFirstNameAndLastName("John", "Boyd");

		//THEN
		Assertions.assertEquals(2, PersonneByFirstNameAndLastNameDto.size());
		Assertions.assertEquals("John", PersonneByFirstNameAndLastNameDto.get(0).getFirstName());
		Assertions.assertEquals("Jacob", PersonneByFirstNameAndLastNameDto.get(1).getFirstName());
	}


	@Test
	void getPersonsByAddressStationForFloodAlert() {
		Data data = new Data();

		data.getPersons().add(new Personne("firstName1", "lastName1", "address1","Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("firstName2", "lastName2", "address2", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("firstName3", "lastName3", "address3", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("firstName4", "lastName4", "address4", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("firstName5", "lastName5", "address5", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("firstName6", "lastName6", "address6", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));

		data.getMedicalRecords().add(new MedicalRecord("firstName1", "lastName1", "02/02/2000", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("firstName2", "lastName2", "02/02/2000", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("firstName3", "lastName3", "02/02/2000", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("firstName4", "lastName4", "02/02/2000", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("firstName5", "lastName5", "02/02/2000", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("firstName6", "lastName6", "02/02/2000", new ArrayList<>(), new ArrayList<>()));

		data.getFirestations().add(new Firestation(1, "address1"));
		data.getFirestations().add(new Firestation(1, "address2"));
		data.getFirestations().add(new Firestation(1, "address3"));
		data.getFirestations().add(new Firestation(2, "address4"));
		data.getFirestations().add(new Firestation(3, "address5"));
		data.getFirestations().add(new Firestation(3, "address6"));

		List<Integer> stations = new ArrayList<>();
		stations.add(1);
		stations.add(3);

		//WHEN
		when(mockDataStorage.getData()).thenReturn(data);
		when(mockDataStorage.getPersonnes()).thenReturn(data.getPersons());
		when(mockDataStorage.getMedicalrecords()).thenReturn(data.getMedicalRecords());
		when(mockDataStorage.getFirestations()).thenReturn(data.getFirestations());

		Assertions.assertEquals(5, PersonneManager.getPersonsByAddressStationForFloodAlert(stations).size());
	}


	@Test
	void getChildrenByAddress() {
		//GIVEN
		Data data = new Data();

		data.getPersons().add(new Personne("adult1", "adult1", "address1", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("children1", "children1", "address1", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("children4", "children4", "address1", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("children5", "children5", "address1", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("children6", "children6", "address1", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));

		data.getPersons().add(new Personne("adult2", "adult2", "address2", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("children2", "children2", "address2", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));
		data.getPersons().add(new Personne("children3", "childre3", "address2", "Culver", 97451, "841-874-0000", "jaboyd@email.com"));

		data.getMedicalRecords().add(new MedicalRecord("adult1", "adult1", "02/02/1983", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("children1", "children1", "02/02/2013", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("adult2", "adult2", "02/02/1983", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("children2", "children2", "02/02/2013", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("children3", "children3", "02/02/2013", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("children4", "children4", "02/02/2013", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("children5", "children5", "02/02/2013", new ArrayList<>(), new ArrayList<>()));
		data.getMedicalRecords().add(new MedicalRecord("children6", "children6", "02/02/2013", new ArrayList<>(), new ArrayList<>()));

		//WHEN
		when(mockDataStorage.getPersonnes()).thenReturn(data.getPersons());
		when(mockDataStorage.getMedicalrecords()).thenReturn(data.getMedicalRecords());

		ChildListAndFamilyListDto childListAndFamilyListDto = PersonneManager.getChildrenByAddress("address1");

		//THEN
		Assertions.assertEquals(4, childListAndFamilyListDto.getGetChildrenByAddressDto().size());
		Assertions.assertEquals(1, childListAndFamilyListDto.getGetAdultsByAddressDto().size());

	}
}