package br.com.leandro.volvo.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.leandro.volvo.config.DepartmentTestMass;
import br.com.leandro.volvo.exception.handler.BaseExceptionHandler;
import br.com.leandro.volvo.service.DepartmentCrudService;

public class DepartmentCrudControllerTest {

	private AutoCloseable autoCloseable;

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype());

	protected MockMvc mockMvc;
	protected HttpHeaders httpHeaders;

	@InjectMocks
	private DepartmentCrudController controller;

	@Mock
	private DepartmentCrudService service;

	@Before
	public void setup() {
		autoCloseable = MockitoAnnotations.openMocks(this);

		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new BaseExceptionHandler(new ObjectMapper())).build();
	}

	@After
	public void close() throws Exception {
		autoCloseable.close();
	}

	@Test
	public void shouldGetDepartment() throws Exception {
		Mockito.when(service.getById(Mockito.anyLong())).thenReturn(DepartmentTestMass.buildDepartmentResponse());

		//@formatter:off
		mockMvc.perform(get("/api/v1/department/1").headers(httpHeaders))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(contentType))
			.andExpect(jsonPath("$", Matchers.notNullValue()))
			.andExpect(jsonPath("$.*", Matchers.hasSize(4)))
			.andExpect(jsonPath("$.id", Matchers.notNullValue()))
			.andExpect(jsonPath("$.name", Matchers.notNullValue()))
			.andExpect(jsonPath("$.description", Matchers.notNullValue()))
			.andExpect(jsonPath("$.users", Matchers.notNullValue()))
			;
		//@formatter:on

		Mockito.verify(service).getById(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void shouldGetAllDepartments() throws Exception {
		Mockito.when(service.getAll()).thenReturn(DepartmentTestMass.buildListDepartmentResponse());

		//@formatter:off
		mockMvc.perform(get("/api/v1/department").headers(httpHeaders))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(contentType))
			.andExpect(jsonPath("$", Matchers.notNullValue()))
			.andExpect(jsonPath("$.*", Matchers.hasSize(2)))
			.andExpect(jsonPath("$[0].id", Matchers.notNullValue()))
			.andExpect(jsonPath("$[0].name", Matchers.notNullValue()))
			.andExpect(jsonPath("$[0].description", Matchers.notNullValue()))
			.andExpect(jsonPath("$[0].users", Matchers.notNullValue()))
			;
		//@formatter:on
		Mockito.verify(service).getAll();
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void shouldCreateDepatment() throws Exception {
		Mockito.when(service.create(Mockito.any())).thenReturn(DepartmentTestMass.buildDepartmentResponse());

		//@formatter:off
		mockMvc.perform(post("/api/v1/department").headers(httpHeaders)
				.content(DepartmentTestMass.buildDepartmentRequestAsString()))
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(contentType))
			.andExpect(jsonPath("$", Matchers.notNullValue()))
			.andExpect(jsonPath("$.*", Matchers.hasSize(4)))
			.andExpect(jsonPath("$.id", Matchers.notNullValue()))
			.andExpect(jsonPath("$.name", Matchers.notNullValue()))
			.andExpect(jsonPath("$.description", Matchers.notNullValue()))
			.andExpect(jsonPath("$.users", Matchers.notNullValue()))
			;
		//@formatter:on

		Mockito.verify(service).create(Mockito.any());
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void shouldUpdateDepatment() throws Exception {
		Mockito.when(service.update(Mockito.anyLong(), Mockito.any()))
				.thenReturn(DepartmentTestMass.buildDepartmentResponse());

		//@formatter:off
		mockMvc.perform(put("/api/v1/department/1").headers(httpHeaders)
				.content(DepartmentTestMass.buildDepartmentRequestAsString()))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(contentType))
			.andExpect(jsonPath("$", Matchers.notNullValue()))
			.andExpect(jsonPath("$.*", Matchers.hasSize(4)))
			.andExpect(jsonPath("$.id", Matchers.notNullValue()))
			.andExpect(jsonPath("$.name", Matchers.notNullValue()))
			.andExpect(jsonPath("$.description", Matchers.notNullValue()))
			.andExpect(jsonPath("$.users", Matchers.notNullValue()))
			;
		//@formatter:on

		Mockito.verify(service).update(Mockito.eq(1L), Mockito.any());
		Mockito.verifyNoMoreInteractions(service);
	}

	@Test
	public void shouldDeleteDepatment() throws Exception {

		//@formatter:off
		mockMvc.perform(delete("/api/v1/department/1").headers(httpHeaders))
			.andExpect(status().isNoContent())
			;
		//@formatter:on

		Mockito.verify(service).delete(Mockito.eq(1L));
		Mockito.verifyNoMoreInteractions(service);
	}

}
