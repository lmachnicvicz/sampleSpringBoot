package br.com.leandro.volvo.service;

import java.util.List;

import br.com.leandro.volvo.dto.DepartmentRequest;
import br.com.leandro.volvo.dto.DepartmentResponse;

public interface DepartmentCrudService {

	DepartmentResponse getById(long id);

	DepartmentResponse create(DepartmentRequest department);

	void delete(long id);

	DepartmentResponse update(long id, DepartmentRequest departmentDto);

	List<DepartmentResponse> getAll();

}
