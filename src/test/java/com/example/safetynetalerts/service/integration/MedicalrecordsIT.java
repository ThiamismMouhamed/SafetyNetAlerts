package com.example.safetynetalerts.service.integration;

import com.example.safetynetalerts.exeptions.BadRequestExeptions;
import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.model.Personne;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.medicalrecord.MedicalRecordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;

@SpringBootTest()
class MedicalrecordsIT {

	@Autowired
	private DataStorageService dataStorage;

	@Autowired
	private MedicalRecordService medicalrecordsManager;

	private MedicalRecord medicalrecord;

	@BeforeEach
	void setUp() throws IOException {
		dataStorage = new DataStorageService();
		medicalrecordsManager = new MedicalRecordService(dataStorage);
	}

	@Test
	void addMedicalrecord() {

		//GIVEN
		List<String> medications = new ArrayList<>();
		List<String> allergies = new ArrayList<>();
		medicalrecord = new MedicalRecord("Jeremy", "Charpentier", "02/23/1984", medications, allergies);

		//WHEN
		medicalrecordsManager.addMedicalRecord(medicalrecord);

		//THEN
		Assertions.assertTrue(dataStorage
				.getMedicalrecords()
				.contains(medicalrecord));

	}

	@Test
	void addMedicalrecordException() {
		medicalrecord = new MedicalRecord("Tenley", "Boyd", "01/05/1984", new ArrayList<>(), new ArrayList<>());
		assertThrows(BadRequestExeptions.class, () -> medicalrecordsManager.addMedicalRecord(medicalrecord));
	}

	@Test
	void updateMedicalrecord() {
		medicalrecord = new MedicalRecord("John", "Boyd", "01/05/1984", new ArrayList<>(), new ArrayList<>());

		medicalrecordsManager.updateMedicalRecord(medicalrecord);

		Assertions.assertTrue(dataStorage
				.getMedicalrecords()
				.stream()
				.anyMatch(p -> p.getFirstName().equals("John") && p.getLastName().equals("Boyd")));
	}

	@Test
	void updateMedicalrecordException() {
		medicalrecord = new MedicalRecord("Test", "Test", "01/05/1984", new ArrayList<>(), new ArrayList<>());
		assertThrows(BadRequestExeptions.class, () -> medicalrecordsManager.updateMedicalRecord(medicalrecord));
	}

	@Test
	void deleteMedicalrecord() {
		medicalrecord = new MedicalRecord("Jacob", "Boyd", "01/05/1984", new ArrayList<>(), new ArrayList<>());

		medicalrecordsManager.deleteMedicalRecord("Jacob", "Boyd");

		Assertions.assertTrue(dataStorage
				.getMedicalrecords()
				.stream()
				.noneMatch(p -> p.getFirstName().equals("Jacob") && p.getLastName().equals("Boyd")));
	}

	@Test
	void deleteMedicalrecordException() {
		medicalrecord = new MedicalRecord("Test", "Test", "02/02/1986", new ArrayList<>(), new ArrayList<>());
		assertThrows(BadRequestExeptions.class, () -> medicalrecordsManager.deleteMedicalRecord("Test", "Test"));
	}

	@Test
	void shouldReturnMedicalRecordJohnBoyd() {
		Personne person = new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-0000", "jaboyd@email.com");
		Optional<MedicalRecord> result = medicalrecordsManager.getMedicalRecordByPersonneId(person.getId());

		Assertions.assertEquals(result.get().getFirstName(), person.getFirstName());
	}


}