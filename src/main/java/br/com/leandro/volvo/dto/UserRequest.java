package br.com.leandro.volvo.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class UserRequest {
	@NotNull
	@ApiModelProperty(value = "User Name", example = "Leandro", required = false)
	private String name;
	@NotNull
	@ApiModelProperty(value = "User Description", example = "User description", required = false)
	private String description;
	@NotNull
	@ApiModelProperty(value = "User's department Id", example = "1", required = false)
	private Long departmentId;

	public UserRequest() {
	}

	public UserRequest(String name, String description, Long departmentId) {
		this.name = name;
		this.description = description;
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	@Override
	public String toString() {
		return "UserRequest [name=" + name + ", description=" + description + ", departmentId=" + departmentId + "]";
	}

}
