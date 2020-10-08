package com.qa.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.persistence.domain.Task;
import com.qa.todo.persistence.repo.TaskRepository;

@SpringBootTest
public class TaskServiceUnitTest {

	@Autowired
	private TaskService service;
	
	@MockBean
	private TaskRepository repo;
	
	@MockBean
	private ModelMapper mapper;
	
	private List<Task> tasks;
	private TaskDTO taskDTO;
	private Task testTask;
	private Task testTaskId;

	final Long id = 1L;
	final String testTaskName = "Take over the world";
	
	@BeforeEach
	void init() {
		this.tasks = new ArrayList<>();
		this.testTask = new Task(testTaskName);
		this.tasks.add(testTask);
		this.testTaskId = new Task(testTask.getTaskName());
		this.testTaskId.setId(id);
		this.taskDTO = mapper.map(testTaskId, TaskDTO.class);
	}
	
	@Test
	public void createTest() {
		when(this.repo.save(this.testTask)).thenReturn(this.testTaskId);
		when(this.mapper.map(this.testTaskId, TaskDTO.class)).thenReturn(this.taskDTO);
		TaskDTO expected = this.taskDTO;
		TaskDTO actual = this.service.create(this.testTask);
		assertThat(expected).isEqualTo(actual);
		verify(this.repo, times(1)).findById(this.id);
	}
}
