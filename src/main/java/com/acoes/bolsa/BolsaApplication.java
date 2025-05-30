package com.acoes.bolsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class BolsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(BolsaApplication.class, args);
	}

}
