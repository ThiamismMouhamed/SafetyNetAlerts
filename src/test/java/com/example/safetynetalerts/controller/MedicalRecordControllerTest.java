package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.model.MedicalRecord;
import com.example.safetynetalerts.service.medicalrecord.MedicalRecordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {
	
	
	@MockBean
	MedicalRecordService medicalrecordsManager;
	ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	void init() {
		objectMapper =new ObjectMapper();
		objectMapper.findAndRegisterModules();
	}
	
	@Test
	void addMedicalrecord() throws Exception {
		MedicalRecord medicalrecord = new MedicalRecord("firstName", "lastName", "02/02/1982", new ArrayList<>(), new ArrayList<>());
		mockMvc.perform(post("/medicalRecord?firstName=firstName&lastName=lastname")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalrecord)))
				.andExpect(status().isCreated());
	}
	
	@Test
	void updateMedicalRecord() throws Exception {
		MedicalRecord medicalrecord = new MedicalRecord("John", "Boyd", "02/02/1982", new ArrayList<>(), new ArrayList<>());
		mockMvc.perform(put("/medicalRecord")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(medicalrecord)))
				.andExpect(status().isOk());
	}
	
	@Test
	void deleteMedicalRecord() throws Exception {
		mockMvc.perform(delete("/medicalRecord")
						.queryParam("firstName", "John")
						.queryParam("lastName", "Boyd")
						)
				.andExpect(status().isOk());
	}
}