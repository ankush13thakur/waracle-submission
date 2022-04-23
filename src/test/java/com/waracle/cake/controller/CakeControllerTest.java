package com.waracle.cake.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waracle.cake.entity.Cake;
import com.waracle.cake.service.CakeServiceImpl;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = CakeController.class)
@OverrideAutoConfiguration(enabled = true)
public class CakeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CakeServiceImpl cakeService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetCakes() throws Exception {
		List<Cake> actual = new ArrayList<Cake>();
		actual.add(new Cake("Chocolate cake", "Eagless"));
		when(cakeService.getAllCakes()).thenReturn(actual);
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].name", Matchers.is("Chocolate cake")))
				.andExpect(jsonPath("$[0].type", Matchers.is("Eagless")));
	}

	@Test
	public void testGetCakeById() throws Exception {
		Cake actual = new Cake("Chocolate cake", "Eagless");
		when(cakeService.getCake(anyInt())).thenReturn(actual);
		mockMvc.perform(get("/cakes/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", Matchers.is("Chocolate cake")))
				.andExpect(jsonPath("$.type", Matchers.is("Eagless")));
	}

	@Test
	public void testCreateCake() throws Exception {
		Cake actual = new Cake("Chocolate cake", "Eagless");
		when(cakeService.createCake(any(Cake.class))).thenReturn(actual);
		mockMvc.perform(
				post("/cakes").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(actual)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.name", Matchers.is("Chocolate cake")))
				.andExpect(jsonPath("$.type", Matchers.is("Eagless")));
	}

	@Test
	public void testUpdateCake() throws Exception {
		Cake actual = new Cake("Chocolate cake", "Eagless");
		when(cakeService.updateCake(anyInt(), any(Cake.class))).thenReturn(actual);
		mockMvc.perform(put("/cakes/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(actual))).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", Matchers.is("Chocolate cake")))
				.andExpect(jsonPath("$.type", Matchers.is("Eagless")));
	}

	@Test
	public void testDeleteCake() throws Exception {
		when(cakeService.deleteCake(anyInt())).thenReturn(true);
		mockMvc.perform(delete("/cakes/1")).andExpect(status().isAccepted())
				.andExpect(jsonPath("$", Matchers.is(true)));
	}
}
