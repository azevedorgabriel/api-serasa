package com.gabrielazevedo.apiserasa;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API Serasa Score", version = "1", description = "API para cadastro de pessoa e consulta de Score"))
public class ApiSerasaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSerasaApplication.class, args);
	}

}
