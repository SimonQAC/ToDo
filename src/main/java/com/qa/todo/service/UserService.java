package com.qa.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.qa.todo.dto.UserDTO;
import com.qa.todo.persistence.domain.User;
import com.qa.todo.persistence.repo.UserRepository;

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
	
	
	
}

