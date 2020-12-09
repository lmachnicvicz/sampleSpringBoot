package br.com.leandro.volvo.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String description;

	@OneToMany(mappedBy = "department", orphanRemoval = true)
	private Set<User> users = new HashSet<>();

	protected Department() {
	}

	public Department(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public Department(long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Department(long id, String name, String description, Set<User> users) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.users = users;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUsers(Set<User> users) {
		this.users = users;

		for (User u : users) {
			u.setDepartment(this);
		}
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", name=" + name + ", description=" + description + ", users=" + users + "]";
	}

}
