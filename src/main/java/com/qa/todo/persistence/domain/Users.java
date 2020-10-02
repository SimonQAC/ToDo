package com.qa.todo.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="username", unique=true)
	@Size(min=1, max=30)
	private String name;
	
	@Column(name="timezone")
	private String timezone;
	
	@ManyToOne
	private Tasks tasks;
}
