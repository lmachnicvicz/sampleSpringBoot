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
import br.com.leandro.volvo.config.UserTestMass;
import br.com.leandro.volvo.dto.UserResponse;
import br.com.leandro.volvo.entity.User;
import br.com.leandro.volvo.exception.DepartmentNotFoundException;
import br.com.leandro.volvo.exception.UserNotFoundException;
import br.com.leandro.volvo.repository.DepartmentRepository;
import br.com.leandro.volvo.repository.UserRepository;
import br.com.leandro.volvo.service.UserCrudService;

public class UserCrudServiceImplTest {

	private AutoCloseable closeable;

	@Mock
	private DepartmentRepository departmentRepo;
	@Mock
	private UserRepository userRepo;

	private UserCrudService service;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
		service = new UserCrudServiceImpl(departmentRepo, userRepo);
	}

	@After
	public void close() throws Exception {
		closeable.close();
	}

	@Test
	public void shouldGetUser() {
		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(UserTestMass.buildUser()));
		
		UserResponse response = service.getById(1L);
		
		assertNotNull(response);
		assertEquals(1L, response.getId().longValue());
		assertEquals("mock", response.getName());
		assertEquals("mock_desc", response.getDescription());
		assertEquals(1L, response.getDepartmentId().longValue());
		
		Mockito.verify(userRepo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(departmentRepo, userRepo);
		
	}

	@Test
	public void shouldGetListDepartments() {
		Mockito.when(userRepo.findAll()).thenReturn(UserTestMass.buildListUsers());

		List<UserResponse> list = service.getAll();

		assertNotNull(list);
		assertEquals(2, list.size());

		Mockito.verify(userRepo).findAll();
		Mockito.verifyNoMoreInteractions(departmentRepo, userRepo);
	}

	@Test
	public void shouldCreateUser() {
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

		Mockito.when(userRepo.save(Mockito.any())).thenReturn(UserTestMass.buildUser());
		Mockito.when(departmentRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(DepartmentTestMass.buildDepartment()));
		
		UserResponse response = service.create(UserTestMass.buildUserRequest());

		assertNotNull(response);
		assertEquals(1L, response.getId().longValue());
		assertEquals("mock", response.getName());
		assertEquals(1L, response.getDepartmentId().longValue());

		Mockito.verify(userRepo).save(userCaptor.capture());
		assertEquals(0, userCaptor.getValue().getId());
		Mockito.verify(departmentRepo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(departmentRepo, userRepo);
	}
	
	@Test
	public void shouldThrowExceptionWhenCreatingUserDepartmentNotFound() {
		try {
			service.create(UserTestMass.buildUserRequest());
			fail("Exception expected");
		} catch (Exception e) {
			assertEquals(DepartmentNotFoundException.class, e.getClass());
		}
		
		Mockito.verify(departmentRepo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(departmentRepo, userRepo);
	}

	@Test
	public void shouldUpdateUser() {
		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

		Mockito.when(userRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(UserTestMass.buildUser()));
		Mockito.when(departmentRepo.findById(Mockito.anyLong())).thenReturn(Optional.of(DepartmentTestMass.buildDepartment()));
		Mockito.when(userRepo.save(Mockito.any())).thenReturn(UserTestMass.buildUser());
		
		UserResponse response = service.update(1, UserTestMass.buildUserRequest());

		assertNotNull(response);
		assertEquals(1L, response.getId().longValue());
		assertEquals("mock", response.getName());
		assertEquals(1L, response.getDepartmentId().longValue());

		Mockito.verify(userRepo).save(userCaptor.capture());
		assertEquals(1, userCaptor.getValue().getId());
		Mockito.verify(userRepo).findById(Mockito.eq(1L));
		Mockito.verify(departmentRepo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(departmentRepo, userRepo);
	}

	@Test
	public void shouldThrowExceptionUpdateInvalidUser() {
		try {
			service.update(1, UserTestMass.buildUserRequest());
			fail("Exception wanted");
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
		}

		Mockito.verify(userRepo).findById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(departmentRepo, userRepo);
	}

	@Test
	public void shouldDeleteDepartment() {
		service.delete(1L);

		Mockito.verify(userRepo).deleteById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(departmentRepo, userRepo);
	}

	@Test
	public void shouldThrowExceptionDeleteInvalidDepartment() {
		Mockito.doThrow(new EmptyResultDataAccessException(0)).when(userRepo).deleteById(Mockito.anyLong());

		try {
			service.delete(1L);
			fail("Exception wanted");
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
		}

		Mockito.verify(userRepo).deleteById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(departmentRepo, userRepo);
	}

}
