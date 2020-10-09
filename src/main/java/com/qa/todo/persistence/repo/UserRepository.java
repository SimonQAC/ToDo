package com.qa.todo.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.todo.persistence.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
