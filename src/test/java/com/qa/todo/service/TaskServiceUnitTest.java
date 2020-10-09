package com.qa.todo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	@Test
	void readAllTest() {
		when(repo.findAll()).thenReturn(this.tasks);
		when(this.modelMapper.map(testTaskId, TaskDTO.class)).thenReturn(taskDTO);
		assertThat(this.service.read().isEmpty()).isFalse();
		verify(repo, times(1)).findAll();
	}
	
	@Test
	void readTest() {
		when(this.repo.findById(id)).thenReturn(Optional.of(this.testTaskId));
		when(this.modelMapper.map(testTaskId, TaskDTO.class)).thenReturn(taskDTO);
		assertThat(this.taskDTO).isEqualTo(this.service.read(this.testTaskId.getId()));
		
	}
	
	@Test
	void updateTest() {
		final Long id = 1L;
		TaskDTO newTask = new TaskDTO(null, "test");
		Task task = new Task(null, "test", null);
		task.setId(id);
		Task updatedTask = new Task(newTask.getName());
		updatedTask.setId(id);
		TaskDTO updatedDTO = new TaskDTO(id, updatedTask.getTaskName());
		
		when(this.repo.findById(this.id)).thenReturn(Optional.of(task));
		when(this.repo.save(updatedTask)).thenReturn(updatedTask);
		when(this.modelMapper.map(updatedTask, TaskDTO.class)).thenReturn(updatedDTO);
		assertThat(updatedDTO).isEqualTo(this.service.update(newTask, this.id));
		verify(this.repo,times(1)).findById(1L);
		verify(this.repo, times(1)).save(updatedTask);
		
	}
	
	@Test
	void deleteTest() {
		when(this.repo.existsById(id)).thenReturn(true,false);
		assertThat(this.service.delete(id)).isTrue();
		verify(this.repo, times(1)).deleteById(id);
		verify(this.repo,times(2)).existsById(id);
	}
	
}