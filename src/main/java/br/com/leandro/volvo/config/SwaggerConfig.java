package br.com.leandro.volvo.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiDocketV1() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("api-v1").select()
				.apis(RequestHandlerSelectors.basePackage("br.com.leandro.volvo")).paths(PathSelectors.any()).build()
				.pathMapping("/").apiInfo(getApiInfo("V1"));
	}

	private ApiInfo getApiInfo(String version) {
		// @formatter:off
		return new ApiInfo(
				"API Exemplo Leandro",
				"API de exemplo contendo endpoints de CRUD",
				version,
				"https://localhost",
				new Contact("Leandro", "", ""),
				"Use license",
				"http://license.com.br",
				new ArrayList<>()
				);
		// @formatter:on
	}
}
