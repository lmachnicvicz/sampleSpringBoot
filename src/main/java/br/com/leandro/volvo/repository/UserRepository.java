package br.com.leandro.volvo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leandro.volvo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	List<User> findByName(String name);
}
