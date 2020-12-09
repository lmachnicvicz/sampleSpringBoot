package br.com.leandro.volvo.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.leandro.volvo.dto.DepartmentRequest;
import br.com.leandro.volvo.dto.DepartmentResponse;
import br.com.leandro.volvo.service.DepartmentCrudService;

@Controller
public class DepartmentViewController {

	@Autowired
	private DepartmentCrudService service;

	@GetMapping("/")
	public String showSignUpForm(Model model) {
		List<DepartmentResponse> list = service.getAll();
		model.addAttribute("departments", list.size() == 0 ? null : list);
		return "index";
	}

	@GetMapping("/signup")
	public String showSignUpForm(DepartmentRequest departmentRequest, Model model) {
		return "add-department";
	}

	@PostMapping("/adddepartment")
	public String addDepartment(@Valid DepartmentRequest departmentRequest, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-department";
		}

		service.create(departmentRequest);
		model.addAttribute("departments", service.getAll());
		return "index";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		DepartmentResponse department = service.getById(id);
		model.addAttribute("department", department);
		return "update-department";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, @Valid DepartmentResponse department, BindingResult result,
			Model model) {
		if (result.hasErrors()) {
			return "update-department";
		}

		service.update(id, department);
		model.addAttribute("departments", service.getAll());
		return "index";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		service.delete(id);
		model.addAttribute("departments", service.getAll());
		return "index";
	}
}
