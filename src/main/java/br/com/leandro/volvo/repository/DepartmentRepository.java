package br.com.leandro.volvo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leandro.volvo.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

	List<Department> findByName(String name);
}
