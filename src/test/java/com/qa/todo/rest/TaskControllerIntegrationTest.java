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
import com.qa.todo.dto.TaskDTO;
import com.qa.todo.persistence.domain.Task;
import com.qa.todo.persistence.repo.TaskRepository;

import net.bytebuddy.asm.Advice.This;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

	@Autowired
	private MockMvc mock;
	
	@Autowired
	private TaskRepository repo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private Long id;
	private Task testTask;
	private Task testTaskId;
	private TaskDTO taskDTO;
	
	private TaskDTO maptoDTO(Task task) {
		return this.modelMapper.map(task, TaskDTO.class);
	}
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@BeforeEach
	void init() {
		this.repo.deleteAll();
		this.testTask = new Task("Take over the world pinky");
		this.testTaskId = this.repo.save(testTask);
		this.taskDTO = this.maptoDTO(this.testTaskId);
		this.id = testTaskId.getId();
	}
	
    @Test
    void testCreate() throws Exception{
        this.mock
        .perform(request(HttpMethod.POST, "/task/create").contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(testTask))
                .accept(MediaType.APPLICATION_JSON))
    			.andExpect(status().isCreated())
    			.andExpect(content().json(this.objectMapper.writeValueAsString(taskDTO)));
    }
    
    @Test 
    void testReadAll() throws Exception{
    	List<TaskDTO> tasks = new ArrayList<>();
    	tasks.add(this.taskDTO);
    	String data =
    	this.mock.perform(request(HttpMethod.GET,"/task/readall").accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    	assertEquals(this.objectMapper.writeValueAsString(tasks),data);
    }
    
    @Test 
    void testRead() throws Exception{
    	this.mock.perform(request(HttpMethod.GET,"/task/read/"+this.id).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(content().json(this.objectMapper.writeValueAsString(taskDTO)));
    }
    
    @Test
    void testUpdate() throws Exception{
    	TaskDTO newTask = new TaskDTO(null, "Testificate");
    	Task updatedTask = new Task(newTask.getName());
    	updatedTask.setId(this.id);
    	
    	String out = this.mock
    			.perform(request(HttpMethod.PUT, "/task/update/"+this.id)
    					.accept(MediaType.APPLICATION_JSON)
    					.contentType(MediaType.APPLICATION_JSON)
    					.content(this.objectMapper.writeValueAsString(newTask)))
    			.andExpect(status().isAccepted()).andReturn().getResponse().getContentAsString();
    	
    	assertEquals(this.objectMapper.writeValueAsString(this.maptoDTO(updatedTask)), out);
    }
    
    @Test
    void testDelete() throws Exception{
    	this.mock
    		.perform(request(HttpMethod.DELETE, "/task/delete/"+this.id))
    		.andExpect(status().isNoContent());
    }
    
    
    
}
