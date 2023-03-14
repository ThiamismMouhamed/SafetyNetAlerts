package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.personne.PersonneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmailController.class)
class CommunityEmailControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DataStorageService dataStorage;
	@MockBean
	private PersonneService personManager;

	@Test
	void getAllMailsByCityTest() throws Exception {
		mockMvc.perform(get("/communityEmail?city=Culver"))
				.andExpect(status().isOk());
	}
}