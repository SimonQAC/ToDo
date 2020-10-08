package com.qa.todo.rest;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.todo.dto.UserDTO;
import com.qa.todo.persistence.domain.User;
import com.qa.todo.persistence.repo.UserRepository;

import io.swagger.models.HttpMethod;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

	@Autowired
	private MockMvc mock;
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private Long id;
	private User testUser;
	private User testUserId;
	private UserDTO userDTO;
	
	private UserDTO maptoDTO(User user) {
		return this.modelMapper.map(user, UserDTO.class);
	}
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void init() {
		this.testUser = new User("Testificate", "UTC");
		this.testUserId = this.repo.save(testUser);
		this.id = testUserId.getId();
	}
	
	@Test
	void createTest() {
		this.mock.perform(request(HttpMethod.POST, "/user/create").contentType(MediaType.APPLICATION_JSON)
				.content(this.objectMapper.writeValueAsString(testUser))
				.accept(MediaType.APPLICATION_JSON)
					.andExpect(status().isCreated())
					.andExpect(content().json(this.objectMapper.writeValueAsString(userDTO))
				));
		
	}
}
