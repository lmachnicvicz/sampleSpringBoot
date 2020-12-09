package br.com.leandro.volvo.service;

import java.util.List;

import br.com.leandro.volvo.dto.UserRequest;
import br.com.leandro.volvo.dto.UserResponse;

public interface UserCrudService {

	List<UserResponse> getAll();
	
	UserResponse getById(long id);

	UserResponse create(UserRequest userDto);
	
	UserResponse update(long id, UserRequest userDto);

	void delete(long id);

}
