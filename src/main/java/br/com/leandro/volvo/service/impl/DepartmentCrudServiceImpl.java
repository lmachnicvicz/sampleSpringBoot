package br.com.leandro.volvo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.leandro.volvo.dto.DepartmentRequest;
import br.com.leandro.volvo.dto.DepartmentResponse;
import br.com.leandro.volvo.dto.UserResponse;
import br.com.leandro.volvo.entity.Department;
import br.com.leandro.volvo.entity.User;
import br.com.leandro.volvo.exception.DepartmentNotFoundException;
import br.com.leandro.volvo.repository.DepartmentRepository;
import br.com.leandro.volvo.service.DepartmentCrudService;

@Service
public class DepartmentCrudServiceImpl implements DepartmentCrudService {

	private DepartmentRepository departmentRepository;

	@Autowired
	public DepartmentCrudServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<DepartmentResponse> getAll() {
		return departmentRepository.findAll().stream().map(this::toDepartmentResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public DepartmentResponse getById(long id) {
		return departmentRepository.findById(id).map(d -> toDepartmentResponse(d)).orElse(null);
	}

	@Override
	@Transactional(readOnly = false)
	public DepartmentResponse create(DepartmentRequest departmentDto) {
		return save(null, departmentDto);
	}

	@Override
	@Transactional(readOnly = false)
	public DepartmentResponse update(long id, DepartmentRequest departmentDto) {
		Department d = departmentRepository.findById(id).orElseThrow(() -> new DepartmentNotFoundException(id));

		return save(d.getId(), departmentDto);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(long id) {
		try {
			departmentRepository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new DepartmentNotFoundException(id, ex);
		}
	}

	private DepartmentResponse save(Long id, DepartmentRequest departmentDto) {
		Department request = toDepartment(departmentDto);

		if (id != null) {
			request.setId(id);
		}

		Department response = departmentRepository.save(request);

		return toDepartmentResponse(response);
	}

	private DepartmentResponse toDepartmentResponse(Department d) {
		List<UserResponse> users = d.getUsers().stream().map(this::toUserResponse).collect(Collectors.toList());

		return new DepartmentResponse(d.getId(), d.getName(), d.getDescription(), users);
	}

	private Department toDepartment(DepartmentRequest d) {
		return new Department(d.getName(), d.getDescription());
	}

	private UserResponse toUserResponse(User u) {
		return new UserResponse(u.getId(), u.getName(), u.getDescription(), u.getDepartment().getId());
	}
}
