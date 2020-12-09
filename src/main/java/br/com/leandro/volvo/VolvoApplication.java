package br.com.leandro.volvo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "br.com.leandro.volvo" })
public class VolvoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolvoApplication.class, args);
	}

}
