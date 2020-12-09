package br.com.leandro.volvo.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class DepartmentRequest {
	@NotNull
	@ApiModelProperty(value = "Department Name", example = "Finance", required = true)
	private String name;
	@NotNull
	@ApiModelProperty(value = "Department Description", example = "Department description", required = true)
	private String description;

	public DepartmentRequest() {
	}

	public DepartmentRequest(String name, String description) {
		this.name = name;
		this.description = description;
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

	@Override
	public String toString() {
		return "DepartmentRequest [name=" + name + ", description=" + description + "]";
	}

}
