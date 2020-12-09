package br.com.leandro.volvo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.leandro.volvo.dto.UserRequest;
import br.com.leandro.volvo.dto.UserResponse;
import br.com.leandro.volvo.entity.Department;
import br.com.leandro.volvo.entity.User;
import br.com.leandro.volvo.exception.DepartmentNotFoundException;
import br.com.leandro.volvo.exception.UserNotFoundException;
import br.com.leandro.volvo.repository.DepartmentRepository;
import br.com.leandro.volvo.repository.UserRepository;
import br.com.leandro.volvo.service.UserCrudService;

@Service
public class UserCrudServiceImpl implements UserCrudService {

	private DepartmentRepository departmentRepository;
	private UserRepository userRepository;

	@Autowired
	public UserCrudServiceImpl(DepartmentRepository departmentRepository, UserRepository userRepository) {
		this.departmentRepository = departmentRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserResponse> getAll() {
		return userRepository.findAll().stream().map(this::toUserDto).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public UserResponse getById(long id) {
		return userRepository.findById(id).map(u -> toUserDto(u)).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public UserResponse create(UserRequest userDto) {
		return save(null, userDto);
	}

	@Override
	@Transactional(readOnly = false)
	public UserResponse update(long id, UserRequest userDto) {
		User u = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

		return save(u.getId(), userDto);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(long id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new UserNotFoundException(id, ex);
		}
	}

	private UserResponse save(Long userId, UserRequest userRequest) {
		Department department = departmentRepository.findById(userRequest.getDepartmentId())
				.orElseThrow(() -> new DepartmentNotFoundException(userRequest.getDepartmentId()));

		User request = toUser(userRequest, department);

		if (userId != null) {
			request.setId(userId);
		}

		User response = userRepository.save(request);

		return toUserDto(response);
	}

	private UserResponse toUserDto(User u) {
		return new UserResponse(u.getId(), u.getName(), u.getDescription(), u.getDepartment().getId());
	}

	private User toUser(UserRequest u, Department d) {
		return new User(u.getName(), u.getDescription(), d);
	}

}
