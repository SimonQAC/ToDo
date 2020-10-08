package com.qa.todo.rest;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.todo.dto.UserDTO;
import com.qa.todo.persistence.domain.User;
import com.qa.todo.persistence.repo.UserRepository;

import net.bytebuddy.asm.Advice.This;

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
		this.repo.deleteAll();
		this.testUser = new User("Testificate", "UTC");
		this.testUserId = this.repo.save(testUser);
		this.userDTO = this.maptoDTO(this.testUserId);
		this.id = testUserId.getId();
	}
	
    @Test
    void testCreate() throws Exception{
        this.mock
        .perform(request(HttpMethod.POST, "/user/create").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(testUser))
                .accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isCreated())
    			.andExpect(content().json(this.objectMapper.writeValueAsString(userDTO)));
    }
    
    @Test 
    void testReadAll() throws Exception{
    	List<UserDTO> users = new ArrayList<>();
    	users.add(this.userDTO);
    	String data =
    	this.mock.perform(request(HttpMethod.GET,"/user/readall").accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    	assertEquals(this.objectMapper.writeValueAsString(users),data);
    }
    
    @Test 
    void testRead() throws Exception{
    	this.mock.perform(request(HttpMethod.GET,"/user/read/"+this.id).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(content().json(this.objectMapper.writeValueAsString(userDTO)));
    }
    
    @Test
    void testUpdate() throws Exception{
    	UserDTO newUser = new UserDTO(null, "Testificate","UTC",null);
    	User updatedUser = new User(newUser.getName(),newUser.getTimezone());
    	updatedUser.setId(this.id);
    	
    	String out = this.mock
    			.perform(request(HttpMethod.PUT, "/user/update/"+this.id)
    					.accept(MediaType.APPLICATION_JSON)
    					.contentType(MediaType.APPLICATION_JSON)
    					.content(this.objectMapper.writeValueAsString(newUser)))
    			.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();
    	
    	assertEquals(this.objectMapper.writeValueAsString(this.maptoDTO(updatedUser)), out);
    }
    
    
    
}
