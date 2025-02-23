package com.example.project_module5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
        (exclude = LiquibaseAutoConfiguration.class)
//create file application-secret.yaml with
// token:
//  signing:
//    key:  your key
//polygon:
//  signing:
//    key:  your key
public class ProjectModule5Application {

    public static void main(String[] args) {
        SpringApplication.run(ProjectModule5Application.class, args);
    }

}
