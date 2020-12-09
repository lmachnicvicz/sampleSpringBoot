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

import br.com.leandro.volvo.dto.UserRequest;
import br.com.leandro.volvo.dto.UserResponse;
import br.com.leandro.volvo.service.UserCrudService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/api/v1/user")
@Api(value = "Endpoint containing Crud operations Users entity")
public class UserCrudController {

	@Autowired
	private UserCrudService service;

	@GetMapping()
	@ApiOperation(value = "Endpoint to get all Users")
	public ResponseEntity<List<UserResponse>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("{id}")
	@ApiOperation(value = "Endpoint to search for a User")
	public ResponseEntity<UserResponse> getById(
			@ApiParam(name = "id", value = "User Id", example = "1") @PathVariable(name = "id", required = true) long id) {

		UserResponse result = service.getById(id);

		if (result != null) {
			return ResponseEntity.ok(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping()
	@ApiOperation(value = "Endpoint to create a User")
	public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request) {
		UserResponse response = service.create(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("{id}")
	@ApiOperation(value = "Endpoint to update a User")
	public ResponseEntity<UserResponse> update(
			@ApiParam(name = "id", value = "User Id", example = "1") @PathVariable(name = "id", required = true) long id,
			@RequestBody @Valid UserRequest request) {
		UserResponse response = service.update(id, request);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("{id}")
	@ApiOperation(value = "Endpoint to delete a User")
	public ResponseEntity<Object> delete(
			@ApiParam(name = "id", value = "User Id", example = "1") @PathVariable(name = "id", required = true) long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
