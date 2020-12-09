package br.com.leandro.volvo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;

import br.com.leandro.volvo.config.DepartmentTestMass;
import br.com.leandro.volvo.dto.DepartmentResponse;
import br.com.leandro.volvo.entity.Department;
import br.com.leandro.volvo.exception.DepartmentNotFoundException;
import br.com.leandro.volvo.repository.DepartmentRepository;
import br.com.leandro.volvo.service.DepartmentCrudService;

public class DepartmentCrudServiceImplTest {

	private AutoCloseable closeable;

	@Mock
	private DepartmentRepository repo;

	private DepartmentCrudService service;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		service = new DepartmentCrudServiceImpl(repo);
	}

	@After
	public void close() throws Exception {
		closeable.close();
	}

	@Test
	public void shouldGetDepartmentWithoutUsers() {
		Mockito.when(repo.findById(Mockito.eq(1L))).thenReturn(Optional.of(DepartmentTestMass.buildDepartment()));

		DepartmentResponse response = service.getById(1L);

		assertNotNull(response);
		assertEquals(1L, response.getId().longValue());
		assertEquals("mock", response.getName());
		assertEquals(0, response.getUsers().size());

		Mockito.verify(repo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(repo);
	}

	@Test
	public void shouldGetDepartmentWithUsers() {
		Mockito.when(repo.findById(Mockito.eq(1L)))
				.thenReturn(Optional.of(DepartmentTestMass.buildDepartmentWithUsers()));

		DepartmentResponse response = service.getById(1L);

		assertNotNull(response);
		assertEquals(1L, response.getId().longValue());
		assertEquals("mock", response.getName());
		assertEquals(1, response.getUsers().size());

		Mockito.verify(repo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(repo);
	}

	@Test
	public void shouldGetListDepartments() {
		Mockito.when(repo.findAll()).thenReturn(DepartmentTestMass.buildDepartmentList());

		List<DepartmentResponse> list = service.getAll();

		assertNotNull(list);
		assertEquals(2, list.size());

		Mockito.verify(repo).findAll();
		Mockito.verifyNoMoreInteractions(repo);
	}

	@Test
	public void shouldCreateDepartment() {
		ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);

		Mockito.when(repo.save(Mockito.any())).thenReturn(DepartmentTestMass.buildDepartment());

		DepartmentResponse response = service.create(DepartmentTestMass.buildDepartmentRequest());

		assertNotNull(response);
		assertEquals(1L, response.getId().longValue());
		assertEquals("mock", response.getName());

		Mockito.verify(repo).save(departmentCaptor.capture());
		assertEquals(0, departmentCaptor.getValue().getId());

		Mockito.verifyNoMoreInteractions(repo);
	}

	@Test
	public void shouldUpdateDepartment() {
		ArgumentCaptor<Department> departmentCaptor = ArgumentCaptor.forClass(Department.class);
		Mockito.when(repo.findById(Mockito.eq(1L))).thenReturn(Optional.of(DepartmentTestMass.buildDepartment()));
		Mockito.when(repo.save(Mockito.any())).thenReturn(DepartmentTestMass.buildDepartment());

		DepartmentResponse response = service.update(1, DepartmentTestMass.buildDepartmentRequest());

		assertNotNull(response);
		assertEquals(1L, response.getId().longValue());
		assertEquals("mock", response.getName());

		Mockito.verify(repo).save(departmentCaptor.capture());
		assertEquals(1, departmentCaptor.getValue().getId());
		Mockito.verify(repo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(repo);
	}

	@Test
	public void shouldThrowExceptionUpdateInvalidDepartment() {
		try {
			service.update(1, DepartmentTestMass.buildDepartmentRequest());
			fail("Exception wanted");
		} catch (Exception e) {
			assertEquals(DepartmentNotFoundException.class, e.getClass());
		}

		Mockito.verify(repo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(repo);
	}

	@Test
	public void shouldDeleteDepartment() {
		service.delete(1L);

		Mockito.verify(repo).deleteById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(repo);
	}

	@Test
	public void shouldThrowExceptionDeleteInvalidDepartment() {
		Mockito.doThrow(new EmptyResultDataAccessException(0)).when(repo).deleteById(Mockito.anyLong());

		try {
			service.delete(1L);
			fail("Exception wanted");
		} catch (Exception e) {
			assertEquals(DepartmentNotFoundException.class, e.getClass());
		}

		Mockito.verify(repo).deleteById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(repo);
	}

}
