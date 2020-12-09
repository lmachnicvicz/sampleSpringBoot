package br.com.leandro.volvo.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.leandro.volvo.entity.Department;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentRepositoryTest {

	@Autowired
	private DepartmentRepository repo;

	@Before
	public void setup() {
		repo.deleteAll();
	}

	@Test
	public void shouldSaveDepartment() {
		Department d = new Department("mock", "mock_desc");
		Department result = repo.save(d);

		assertNotNull(result);
		assertNotNull(d.getId());
		assertEquals("mock", d.getName());
		assertEquals("mock_desc", d.getDescription());
	}

	@Test
	public void shouldGetDepartment() {
		Department d = new Department("mock", "mock_desc");
		Department saved = repo.save(d);
		
		Department result = repo.findById(saved.getId()).orElse(null);
		
		assertNotNull(result);
		assertNotNull(d.getId());
		assertEquals("mock", d.getName());
		assertEquals("mock_desc", d.getDescription());
	}

}
