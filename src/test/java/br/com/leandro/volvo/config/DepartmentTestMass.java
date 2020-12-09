package br.com.leandro.volvo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.leandro.volvo.dto.DepartmentRequest;
import br.com.leandro.volvo.dto.DepartmentResponse;
import br.com.leandro.volvo.entity.Department;
import br.com.leandro.volvo.entity.User;

public class DepartmentTestMass {
	public static Department buildDepartment() {
		return new Department(1, "mock", "mock_desc");
	}

	public static Department buildDepartmentWithUsers() {
		Set<User> users = new HashSet<>(Arrays.asList(UserTestMass.buildUser()));
		return new Department(1, "mock", "mock_desc", users);
	}

	public static List<Department> buildDepartmentList() {
		return Arrays.asList(new Department(1, "mock", "mock_desc"), new Department(2, "mock2", "mock_desc2"));
	}

	public static DepartmentRequest buildDepartmentRequest() {
		return new DepartmentRequest("mock", "mock_desc");
	}

	public static DepartmentResponse buildDepartmentResponse() {
		return new DepartmentResponse(1L, "mock", "mock_desc", new ArrayList<>());
	}

	public static List<DepartmentResponse> buildListDepartmentResponse() {
		return Arrays.asList(new DepartmentResponse(1L, "mock", "mock_desc", new ArrayList<>()),
				new DepartmentResponse(2L, "mock", "mock_desc", new ArrayList<>()));
	}

	public static String buildDepartmentRequestAsString() {
		//@formatter:off
		return "{\r\n" + 
				"  \"description\": \"Department description\",\r\n" + 
				"  \"name\": \"Finance1\"\r\n" + 
				"}";
		//@formatter:on
	}
}
