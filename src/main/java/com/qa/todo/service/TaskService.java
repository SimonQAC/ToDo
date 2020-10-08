package com.qa.todo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.exception.TaskNotFoundException;
import com.qa.todo.exception.UserNotFoundException;
import com.qa.todo.persistence.domain.Task;
import com.qa.todo.persistence.repo.TaskRepository;
import com.qa.todo.utils.SpringBeanUtils;

@Service
public class TaskService {

	private TaskRepository repo;
	
	private ModelMapper mapper;
	
	@Autowired
	public TaskService(TaskRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private TaskDTO mapToDTO(Task task) {
		return this.mapper.map(task, TaskDTO.class);
	}
		
	private Task mapFromDTO(TaskDTO taskDTO) {
		return this.mapper.map(taskDTO,  Task.class);
	}
	
    public TaskDTO create(Task task) {
        Task created = this.repo.save(task);
        TaskDTO mapped = this.mapToDTO(created);
        return mapped;
    }
	
    public List<TaskDTO> read() {
        List<Task> found = this.repo.findAll();
        List<TaskDTO> streamed = found.stream().map(this::mapToDTO).collect(Collectors.toList());
        return streamed;
    }
    
    public TaskDTO read(Long id) {
        Task found = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
        return this.mapToDTO(found);
    }
	

	public TaskDTO update(TaskDTO taskDTO, Long id) {
		Task toUpdate = this.repo.findById(id).orElseThrow(TaskNotFoundException::new);
		toUpdate.setTaskName(taskDTO.getName());
		SpringBeanUtils.mergeObject(taskDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	

	public Boolean delete(Long id) {
		if(!this.repo.existsById(id)) {
			throw new UserNotFoundException();
		}
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
//		this.repo.deleteById(id);
//		return this.repo.existsById(id);
	}
	
}
