package br.com.leandro.volvo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import br.com.leandro.volvo.entity.Department;
import br.com.leandro.volvo.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

	@Autowired
	private DepartmentRepository departmentRepo;

	private Department mock_department;

	@Autowired
	private UserRepository userRepo;

	@Before
	public void setup() {
		userRepo.deleteAll();
		departmentRepo.deleteAll();
		Department d = new Department("mock", "mock_desc");
		mock_department = departmentRepo.save(d);

	}

	@Test
	public void shouldSaveUser() {
		User u = new User("mock", "mock_desc", mock_department);

		User result = userRepo.save(u);

		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals("mock", result.getName());
		assertEquals("mock_desc", result.getDescription());
		assertNotNull(result.getDepartment());
		assertEquals("mock", result.getDepartment().getName());
	}

	@Test
	@Transactional
	public void shouldGetUser() {
		User u = new User("mock", "mock_desc", mock_department);

		User saved = userRepo.save(u);

		User result = userRepo.findById(saved.getId()).orElse(null);
		
		assertNotNull(result);
		assertNotNull(result.getId());
		assertEquals("mock", result.getName());
		assertEquals("mock_desc", result.getDescription());
		assertNotNull(result.getDepartment());
		assertEquals("mock", result.getDepartment().getName());
	}

	@Test
	public void shouldNotSaveUserDepartmentNotFound() {
		Department d = new Department(99, "mock", "mock_desc");
		User u = new User("mock", "mock_desc", d);
		try {
			userRepo.save(u);
			fail("Exception expected");
		} catch (Exception e) {
			assertEquals(DataIntegrityViolationException.class, e.getClass());
		}

	}

}
