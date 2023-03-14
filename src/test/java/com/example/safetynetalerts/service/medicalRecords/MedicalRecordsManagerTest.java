package com.example.safetynetalerts.service.medicalRecords;

import com.example.safetynetalerts.exeptions.BadRequestExeptions;
import com.example.safetynetalerts.model.Data;
import com.example.safetynetalerts.model.Id;
import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.medicalrecord.MedicalRecordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MedicalRecordsManagerTest {

	MedicalRecordService medicalrecordsManager;

	@Mock
	DataStorageService mockDataStorage;

	@BeforeEach
	public void Init() {
		medicalrecordsManager = new MedicalRecordService(mockDataStorage);
	}

	@Test
	void addMedicalRecord() {
		//WHEN
		Data datas = new Data();
		Assertions.assertTrue(datas.getMedicalRecords().isEmpty());
		Assertions.assertNotNull(datas.getMedicalRecords());
		MedicalRecord medicalrecordToAdd = new MedicalRecord("firstNameTest", "lastNameTest", "02/02/1933", new ArrayList<>(), new ArrayList<>());


		//WHEN
		when(mockDataStorage.getData()).thenReturn(datas);
		when(mockDataStorage.getMedicalrecords()).thenReturn(datas.getMedicalRecords());
		medicalrecordsManager.addMedicalRecord(medicalrecordToAdd);


		//THEN
		Assertions.assertFalse(datas.getMedicalRecords().isEmpty());
		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertEquals(1, datas.getMedicalRecords().size());

		MedicalRecord medicalrecordAdded = datas.getMedicalRecords().get(0);
		Assertions.assertEquals(medicalrecordToAdd, medicalrecordAdded);

	}

	@Test
	void addMedicalRecordAlreadyExisting() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertTrue(datas.getMedicalRecords().isEmpty());
		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertEquals(0, datas.getMedicalRecords().size());

		MedicalRecord medicalrecordExisting = new MedicalRecord("firstNameTest", "lastNameTest", "02/02/1933", new ArrayList<>(), new ArrayList<>());
		datas.getMedicalRecords().add(medicalrecordExisting);

		//WHEN
		when(mockDataStorage.getMedicalrecords()).thenReturn(datas.getMedicalRecords());
		when(mockDataStorage.getMedicalRecordById(any())).thenReturn(Optional.of(medicalrecordExisting));

		//THEN
		Assertions.assertFalse(datas.getMedicalRecords().isEmpty());
		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertEquals(1, datas.getMedicalRecords().size());

		MedicalRecord medicalrecordToAdd = new MedicalRecord("firstNameTest", "lastNameTest", "02/02/1933", new ArrayList<>(), new ArrayList<>());
		Assertions.assertThrows(BadRequestExeptions.class, () -> medicalrecordsManager.addMedicalRecord(medicalrecordToAdd));

	}

	@Test
	void updateMedicalrecord() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertTrue(datas.getMedicalRecords().isEmpty());
		Assertions.assertNotNull(datas.getMedicalRecords());

		MedicalRecord existingMedicalrecord = new MedicalRecord("firstNametest", "lastNameTest", "02/02/1933", new ArrayList<>(), new ArrayList<>());
		datas.getMedicalRecords().add(existingMedicalrecord);

		MedicalRecord medicalrecordUpdate = new MedicalRecord("firstNametest", "lastNameTest", "02/02/1933", new ArrayList<>(), new ArrayList<>());
		medicalrecordUpdate.setBirthdate("03/03/1983");

		//WHEN
		when(mockDataStorage.getMedicalrecords()).thenReturn(datas.getMedicalRecords());
		when(mockDataStorage.getMedicalRecordById(any())).thenReturn(Optional.of(existingMedicalrecord));


		medicalrecordsManager.updateMedicalRecord(medicalrecordUpdate);
		Assertions.assertEquals("1983-03-03", datas.getMedicalRecords().get(0).getBirthdate().toString());

		//THEN
		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertFalse(datas.getMedicalRecords().isEmpty());
		Assertions.assertEquals(1, datas.getMedicalRecords().size());

		MedicalRecord medicalrecordUpdated = datas.getMedicalRecords().get(0);
		Assertions.assertEquals(medicalrecordUpdate, medicalrecordUpdated);
	}

	@Test
	void updateMedicalrecordPersonNotExisting() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertTrue(datas.getMedicalRecords().isEmpty());
		Assertions.assertNotNull(datas.getMedicalRecords());

		MedicalRecord medicalrecordUpdate = new MedicalRecord("firstNametest", "lastNameTest", "02/02/1933", new ArrayList<>(), new ArrayList<>());
		medicalrecordUpdate.setBirthdate("03/03/1983");

		//WHEN
		when(mockDataStorage.getMedicalrecords()).thenReturn(datas.getMedicalRecords());

		//THEN
		Assertions.assertThrows(BadRequestExeptions.class, () -> medicalrecordsManager.updateMedicalRecord(medicalrecordUpdate));
	}

	@Test
	void deleteMedicalRecord() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertTrue(datas.getMedicalRecords().isEmpty());

		MedicalRecord existingMedicalRecord = new MedicalRecord("firstNametest", "lastNameTest", "02/02/1933", new ArrayList<>(), new ArrayList<>());
		datas.getMedicalRecords().add(existingMedicalRecord);
		Assertions.assertEquals(1, datas.getMedicalRecords().size());

		//WHEN
		when(mockDataStorage.getMedicalrecords()).thenReturn(datas.getMedicalRecords());
		when(mockDataStorage.getMedicalRecordById(any())).thenReturn(Optional.of(existingMedicalRecord));


		//THEN
		medicalrecordsManager.deleteMedicalRecord("firstNametest", "lastNameTest");

		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertTrue(datas.getMedicalRecords().isEmpty());
		Assertions.assertEquals(0, datas.getMedicalRecords().size());
	}

	@Test
	void deleteMedicalRecordNotExisting() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertTrue(datas.getMedicalRecords().isEmpty());

		//WHEN
		when(mockDataStorage.getMedicalrecords()).thenReturn(datas.getMedicalRecords());

		//THEN
		Assertions.assertThrows(BadRequestExeptions.class, () -> medicalrecordsManager.deleteMedicalRecord("firstNametest1", "lastNameTest1"));

	}

	@Test
	void getMedicalRecordByPersonId() {
		//GIVEN
		Data datas = new Data();
		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertTrue(datas.getMedicalRecords().isEmpty());

		MedicalRecord medicalrecordExisting = new MedicalRecord("firstNameTest", "lastNameTest", "02/02/1933", new ArrayList<>(), new ArrayList<>());
		datas.getMedicalRecords().add(medicalrecordExisting);

		//WHEN
		when(mockDataStorage.getData()).thenReturn(datas);
		when(mockDataStorage.getMedicalrecords()).thenReturn(datas.getMedicalRecords());

		medicalrecordsManager.getMedicalRecordByPersonneId(medicalrecordExisting.getId());
		Optional<MedicalRecord> medicalRecordID = medicalrecordsManager.getMedicalRecordByPersonneId(new Id("firstNameTest", "lastNameTest"));

		//THEN
		Assertions.assertNotNull(datas.getMedicalRecords());
		Assertions.assertFalse(datas.getMedicalRecords().isEmpty());

		Assertions.assertEquals(Optional.of(medicalrecordExisting), medicalRecordID);

	}
}