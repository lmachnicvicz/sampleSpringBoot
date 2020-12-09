package br.com.leandro.volvo.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leandro.volvo.dto.DepartmentRequest;
import br.com.leandro.volvo.dto.DepartmentResponse;
import br.com.leandro.volvo.service.DepartmentCrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/department")
@Api(value = "Endpoint containing Crud operations for Department entity")
public class DepartmentCrudController {

	@Autowired
	private DepartmentCrudService service;

	@GetMapping()
	@ApiOperation(value = "Endpoint to get all Departments")
	public ResponseEntity<List<DepartmentResponse>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("{id}")
	@ApiOperation(value = "Endpoint to search for a Department")
	public ResponseEntity<DepartmentResponse> getById(
			@ApiParam(name = "id", value = "Department Id", example = "1") @PathVariable(name = "id", required = true) long id) {
		DepartmentResponse result = service.getById(id);

		if (result != null) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping()
	@ApiOperation(value = "Endpoint to create a Department")
	public ResponseEntity<DepartmentResponse> create(@RequestBody @Valid DepartmentRequest request) {
		DepartmentResponse response = service.create(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("{id}")
	@ApiOperation(value = "Endpoint to update a Department")
	public ResponseEntity<DepartmentResponse> update(
			@ApiParam(name = "id", value = "Department Id", example = "1") @PathVariable(name = "id", required = true) long id,
			@RequestBody @Valid DepartmentRequest request) {
		DepartmentResponse response = service.update(id, request);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("{id}")
	@ApiOperation(value = "Endpoint to delete a Department")
	public ResponseEntity<Object> delete(
			@ApiParam(name = "id", value = "Department Id", example = "1") @PathVariable(name = "id", required = true) long id) {
		service.delete(id);

		return ResponseEntity.noContent().build();
	}

}
