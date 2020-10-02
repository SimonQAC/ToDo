package com.qa.todo.service;

import org.springframework.stereotype.Service;

import com.qa.todo.persistence.repo.UserRepository;

@Service
public class UserService {

	private UserRepository repo;
	private ModelMapper mapper;
}
