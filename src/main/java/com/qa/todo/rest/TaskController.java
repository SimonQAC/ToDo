package com.qa.todo.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.todo.dto.TaskDTO;
import com.qa.todo.persistence.domain.Task;
import com.qa.todo.persistence.repo.TaskRepository;
import com.qa.todo.service.TaskService;

@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

	
	public TaskService service;

	@Autowired
	public TaskController(TaskService service) {
		super();
		this.service = service;
	}

	
    @PostMapping("/create")
    public ResponseEntity<TaskDTO> create(@RequestBody Task task) {
        TaskDTO created = this.service.create(task);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/readall")
    public ResponseEntity<List<TaskDTO>> read() {
        return ResponseEntity.ok(this.service.read());
    }
	
    @GetMapping("/read/{id}")
    public ResponseEntity<TaskDTO> read(@PathVariable Long id) {
        return ResponseEntity.ok(this.service.read(id));
    }
	
    @PutMapping("/update/{id}")
    public ResponseEntity<TaskDTO> update(@Valid @PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(this.service.update(taskDTO, id), HttpStatus.ACCEPTED);
    }
    
//    @PutMapping("/update/{id}")
//    public ResponseEntity<TaskDTO> updateUser(@Valid @PathVariable Long id, @RequestBody TaskDTO taskDTO) {
//        return TaskRepository.save(taskDTO);
//    }
    
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<TaskDTO> delete(@PathVariable Long id){
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
