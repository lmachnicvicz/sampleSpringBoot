package br.com.leandro.volvo.config;

import java.util.Arrays;
import java.util.List;

import br.com.leandro.volvo.dto.UserRequest;
import br.com.leandro.volvo.dto.UserResponse;
import br.com.leandro.volvo.entity.User;

public class UserTestMass {
	public static User buildUser() {
		return new User(1, "mock", "mock_desc", DepartmentTestMass.buildDepartment());
	}

	public static List<User> buildListUsers() {
		return Arrays.asList(new User(1, "mock", "mock_desc", DepartmentTestMass.buildDepartment()),
				new User(2, "mock2", "mock_desc2", DepartmentTestMass.buildDepartment()));
	}

	public static UserRequest buildUserRequest() {
		return new UserRequest("mock", "mock_desc", 1L);
	}

	public static UserResponse buildUserResponse() {
		return new UserResponse(1L, "mock", "mock_desc", 1L);
	}

	public static List<UserResponse> buildListUserResponse() {
		return Arrays.asList(new UserResponse(1L, "mock", "mock_desc", 1L),
				new UserResponse(2L, "mock", "mock_desc", 2L));
	}

	public static String buildUserRequestAsString() {
		//@formatter:off
		return "{\r\n" + 
				"  \"departmentId\": 1,\r\n" + 
				"  \"description\": \"User description\",\r\n" + 
				"  \"name\": \"name\"\r\n" + 
				"}";
		//@formatter:on
	}
}
