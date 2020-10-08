package com.qa.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
	private ModelMapper modelMapper;	
		
	private List<Task> tasks;
	private Task testTask;
	private Task testTaskId;
	private TaskDTO taskDTO;
	final Long id = 1L;
	
	private TaskDTO maptoDTO(Task task) {
		return this.modelMapper.map(task, TaskDTO.class);
	}
	
	@BeforeEach
	void init() {
		this.tasks = new ArrayList<>();
		this.testTask = new Task("Become ruler of the world");
		this.testTaskId = new Task(testTask.getTaskName());
		this.testTaskId.setId(id);
		this.tasks.add(testTaskId);
		this.taskDTO = this.maptoDTO(testTaskId);
	}
	
	@Test
	public void createTest() {
		when(this.repo.save(testTask)).thenReturn(testTaskId);
		
		when(this.modelMapper.map(testTaskId, TaskDTO.class)).thenReturn(taskDTO);
		
		TaskDTO expected = this.taskDTO;
		TaskDTO actual = this.service.create(testTask);
		
		assertThat(expected).isEqualTo(actual);
		
		verify(this.repo, times(1)).save(this.testTask);
	}
}