package br.com.leandro.volvo.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserResponse extends UserRequest {
	@ApiModelProperty(value = "User Id", example = "1", required = false)
	private Long id;

	public UserResponse() {
	}

	public UserResponse(Long id, String name, String description, Long departmentId) {
		super(name, description, departmentId);
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", getName()=" + getName() + ", getDescription()=" + getDescription()
				+ ", getDepartmentId()=" + getDepartmentId() + "]";
	}

}
