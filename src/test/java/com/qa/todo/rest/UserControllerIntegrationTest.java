package com.qa.todo.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
	private ModelMapper mapper;
	
	private Long id;
	private User testUser;
	private User testUserId;
	
	private UserDTO maptoDTO(User user) {
		return this.mapper.map(user, UserDTO.class);
	}
	
	@BeforeEach
	void init() {
		this.testUser = new User("Testificate", "UTC");
		this.testUserId = this.repo.save(testUser);
		this.id = testUserId.getId();
	}
	
	@Test
	void createTest() {
		this.mock.perform(request(HttpMethod.POST, 
				"/user/create")
				);
		
	}
}
