package com.qa.todo.rest;

import java.util.List;

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

import com.qa.todo.dto.UserDTO;
import com.qa.todo.persistence.domain.User;
import com.qa.todo.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
	
	public UserService service;

	@Autowired
	public UserController (UserService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<UserDTO> create(@RequestBody User user){
		UserDTO created = this.service.create(user);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("/readall")
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		return ResponseEntity.ok(this.service.read());
	}
	
	@GetMapping("/read/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.read(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @RequestBody UserDTO userDTO){
		return new ResponseEntity<>(this.service.update(userDTO, id),HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<UserDTO> delete(@PathVariable Long id){
		return this.service.delete(id)
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
						: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
