package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.model.Personne;
import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.personne.PersonneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = PersonneController.class)
class PersonControllerTest {
	
	
	@MockBean
	PersonneService personManagerImpl;
	
	@MockBean
	DataStorageService dataStorage;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void getPersons() throws Exception {
		mockMvc.perform(get("/person"))
				.andExpect(status().isOk());
	}
	
	@Test
	void addPerson() throws Exception {
		Personne personToAdd = new Personne("newFirstname", "newLastName", "addressTest", "Paris", 75013, "1234", "mail");

		mockMvc.perform(post("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personToAdd)))
				.andExpect(status().isCreated());

	}
	
	@Test
	void updatePerson() throws Exception {
		Personne personToUpdate = (new Personne("firstName", "lastName", "address", "city", 75, "123", "mail"));

		mockMvc.perform(put("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(personToUpdate)))
				.andExpect(status().isOk());
		
	}
	
	@Test
	void deletePerson() throws Exception {
		mockMvc.perform(delete("/person")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								new Personne("John", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6512", "jaboyd@email.com"))))
				.andExpect(status().isOk());
	}
	
	@Test
	void getPersonsByFirstNameAndLastName() throws Exception {
		mockMvc.perform(get("/personInfo?firstName=firstName&lastName=lastName")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(
								new Personne("Jacob", "Boyd", "1509 Culver St", "Culver", 97451, "841-874-6513", "drk@email.com"))))
				.andExpect(status().isOk());
		
	}
}