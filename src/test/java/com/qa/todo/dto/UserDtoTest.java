package com.qa.todo.dto;

import org.junit.jupiter.api.Test;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class UserDtoTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(UserDTO.class).verify();
	}
}
