package com.qa.todo.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.persistence.domain.Task;
import com.qa.todo.service.TaskService;

@SpringBootTest
public class TaskControllerUnitTest {

	@Autowired
	private TaskController controller;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@MockBean
	private TaskService service;
	
	private Task testTask;
	private Task testTaskId;
	private TaskDTO taskDTO;
	private final Long id = 1L;
	
	private List<Task> taskList; 
	
    private TaskDTO mapToDTO(Task task) {
        return this.modelMapper.map(task, TaskDTO.class);
    }
    
    @BeforeEach
    void init() {
    	this.taskList = new ArrayList<>();
    	this.testTask = new Task("Take over the world");
    	this.testTaskId = new Task(testTask.getTaskName());
    	this.testTaskId.setId(id);
    	this.taskList.add(testTaskId);
    	this.taskDTO = this.mapToDTO(testTaskId);
    }
    
    @Test
    public void createTest() {
    	when(this.service.create(testTask)).thenReturn(this.taskDTO);
    	assertThat(new ResponseEntity<TaskDTO>(this.taskDTO, HttpStatus.CREATED))
    		.isEqualTo(this.controller.create(testTask));
    }
}
