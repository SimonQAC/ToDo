package com.qa.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.qa.todo.dto.UserDTO;
import com.qa.todo.exception.UserNotFoundException;
import com.qa.todo.persistence.domain.User;
import com.qa.todo.persistence.repo.UserRepository;
import com.qa.todo.utils.SpringBeanUtils;

@Service
public class UserService {

	private UserRepository repo;
	private ModelMapper mapper;
	
	@Autowired
	public UserService (UserRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private UserDTO mapToDTO (User user) {
		return this.mapper.map(user,  UserDTO.class);
	}
	
	private User mapFromDTO(UserDTO userDTO) {
		return this.mapper.map(userDTO, User.class);
	}
	
	public UserDTO create (User user) {
		User created = this.repo.save(user);
		UserDTO mapped = this.mapToDTO(created);
		return mapped;
	}
	
	public List<UserDTO> read(){
		List<User> found = this.repo.findAll();
		List<UserDTO> streamed = found.stream().map(this::mapToDTO).collect(Collectors.toList());
		return streamed;
	}
	
	public UserDTO read(Long id) {
		User found = this.repo.findById(id).orElseThrow(UserNotFoundException::new);
		return this.mapToDTO(found);
	}
	
	public UserDTO update(UserDTO userDTO, Long id) {
		User toUpdate = this.repo.findById(id).orElseThrow(UserNotFoundException::new);
		SpringBeanUtils.mergeObject(userDTO, toUpdate);
		return this.mapToDTO(this.repo.save(toUpdate));
	}
	
	public boolean delete(Long id) {
		this.repo.deleteById(id);
		return this.repo.existsById(id);
	}
	
}

