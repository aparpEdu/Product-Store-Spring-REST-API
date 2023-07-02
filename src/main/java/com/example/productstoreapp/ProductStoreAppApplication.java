package com.example.productstoreapp;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Product Store App REST APIs",
                description = "Spring Boot Product Store App REST APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Alexandr",
                        email = "alexanderparpulansky@gmail.com",
                        url = "https://github.com/aparpEdu?tab=repositories"
                ),
                license = @License(
                        name = "Apache 2.0"
//                        url =
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Spring Boot Product Store App Documentation",
                url = "https://github.com/aparpEdu/blogApp"
        )
)
public class ProductStoreAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductStoreAppApplication.class, args);
    }

}
