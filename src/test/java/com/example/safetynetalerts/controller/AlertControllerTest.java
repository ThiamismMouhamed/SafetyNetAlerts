package com.example.safetynetalerts.controller;

import com.example.safetynetalerts.service.dataStorage.DataStorageService;
import com.example.safetynetalerts.service.firestation.FirestationService;
import com.example.safetynetalerts.service.personne.PersonneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlertController.class)
class AlertControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DataStorageService dataStorageImpl;
	@MockBean
	private FirestationService firestationManager;
	@MockBean
	private PersonneService personManager;

	@Test
	void getPersonsByAddress() throws Exception {
		String address = "1509 Culver St";
		mockMvc.perform(get("/fire")
						.param("address", address))
				.andExpect(status().isOk());

	}

	@Test
	void getPhoneNumbersByFirestationNumber() throws Exception {
		int firestationNumber = 1;
		mockMvc.perform(get("/phoneAlert")
						.param("firestation", String.valueOf(firestationNumber)))
				.andExpect(status().isOk());
	}

	@Test
	void getChildByAddress() throws Exception {
		String address = "1509 Culver St";
		mockMvc.perform(get("/childAlert")
						.param("address", address))
				.andExpect(status().isOk());
	}

	@Test
	void getPersonsByAddressStationForFloodAlert() throws Exception {
		List<Integer> stationsList = new ArrayList<>();
		stationsList.add(1);
		stationsList.add(3);

		mockMvc.perform(get("/flood/stations/?stations=1&stations=3"))
				.andExpect(status().isOk());
	}
}