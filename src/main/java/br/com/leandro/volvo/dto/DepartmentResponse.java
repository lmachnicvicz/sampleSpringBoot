package br.com.leandro.volvo.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

public class DepartmentResponse extends DepartmentRequest {
	@ApiModelProperty(value = "Department Id", example = "1", required = false)
	private Long id;
	@ApiModelProperty(value = "Department's user list", example = "", required = false)
	private List<UserResponse> users = new ArrayList<>();

	public DepartmentResponse() {

	}

	public DepartmentResponse(Long id, String name, String description, List<UserResponse> users) {
		super(name, description);
		this.id = id;
		this.users = users;
	}

	public DepartmentResponse(List<UserResponse> users) {
		this.users = users;
	}

	public List<UserResponse> getUsers() {
		return users;
	}

	public void setUsers(List<UserResponse> users) {
		this.users = users;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DepartmentResponse [users=" + users + ", id=" + id + ", getName()=" + getName() + ", getDescription()="
				+ getDescription() + "]";
	}

}
