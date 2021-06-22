package com.pokedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PokedexApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PokedexApplication.class);
		app.setLazyInitialization(false);
		app.run(args);
	}

}
