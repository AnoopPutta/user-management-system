package com.qantas.crm.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qantas.crm.Application;
import com.qantas.crm.dal.UserDALImpl;
import com.qantas.crm.dal.UserRepository;
import com.qantas.crm.model.Address;
import com.qantas.crm.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ContextConfiguration(classes = { Application.class })
public class UserControllerTest {

	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserDALImpl userDAL;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

	@Test
	public void createUserAlreadyExists() throws Exception {

		User user = new User();
		user.setUserId("5c82864c33e5d83c5efcaf51");
		user.setEmail("email");
		user.setfirstName("fisrtname");
		user.setLastName("lastName");
		mvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(mapToJson(user)))
		.andExpect(status().isConflict());

	}

	@Test
	public void createUser() throws Exception {

		User user = new User();
		user.setEmail("email1@zyx.com");
		user.setfirstName("fisrtname");
		user.setLastName("lastName");
		Address adr = new Address();
		adr.setAddress("My address");
		adr.setPhone("8888888888");
		adr.setType("home");
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(adr);
		user.setAddresses(addresses);
		Map<String,String> additionalDetails = new HashMap<String,String>();
		additionalDetails.put("detail1", "value1");
		user.setAdditionalDetails(additionalDetails);
		mvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(mapToJson(user)))
		.andExpect(status().isCreated()).andExpect(header().exists("Location"));

	}

	@Test
	public void createUserEmailNull() throws Exception {
		User user = new User();
		user.setUserId("5c82864c33e5d83c5efcaf51");
		user.setfirstName("fisrtname");
		user.setLastName("lastName");
		mvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(mapToJson(user)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void createUserFirstNameNull() throws Exception {
		User user = new User();
		user.setUserId("5c82864c33e5d83c5efcaf51");
		user.setEmail("email");
		user.setLastName("lastName");
		mvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(mapToJson(user)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void createUserLastNameNull() throws Exception {
		User user = new User();
		user.setUserId("5c82864c33e5d83c5efcaf51");
		user.setfirstName("fisrtname");
		user.setEmail("email");
		;
		mvc.perform(post("/api/v1/users").contentType(MediaType.APPLICATION_JSON).content(mapToJson(user)))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void getAllUsers() throws Exception {

		User user = new User();
		user.setUserId("5c82864c33e5d83c5efcaf51");
		user.setEmail("email");
		user.setfirstName("fisrtname");
		user.setLastName("lastName");
		List<User> l = new ArrayList<User>();
		l.add(user);
		userRepository.save(user);
		mvc.perform(get("/api/v1/users")).andExpect(status().isOk());
	}

	@Test
	public void getUser() throws Exception {

		mvc.perform(get("/api/v1/users/5c82864c33e5d83c5efcaf51")).andExpect(status().isOk());
	}

	@Test
	public void deleteUser() throws Exception {

		mvc.perform(delete("/api/v1/users/5c82864c33e5d83c5efcaf51")).andExpect(status().isNoContent());
	}

	@Test
	public void getNonExistingUser() throws Exception {

		mvc.perform(get("/api/v1/users/5c82864c33e5d83c5efcaf52")).andExpect(status().isNotFound());
	}

	@Test
	public void deleteNonExistingUser() throws Exception {

		mvc.perform(delete("/api/v1/users/5c82864c33e5d83c5efcaf52")).andExpect(status().isNotFound());
	}

	@Test
	public void updateExistingUser() throws Exception {
		User user = new User();
		user.setEmail("email");
		user.setfirstName("fisrtname");
		user.setLastName("newLastName");
		mvc.perform(put("/api/v1/users/5c82864c33e5d83c5efcaf51").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(user))).andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(jsonPath("$.lastName", is("newLastName")));
	}

	@Test
	public void updateNonExistingUser() throws Exception {
		User user = new User();
		user.setEmail("email");
		user.setfirstName("fisrtname");
		user.setLastName("newLastName");
		mvc.perform(put("/api/v1/users/5c82864c33e5d83c5efcaf55").contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(user))).andExpect(status().isNotFound());

	}

}
