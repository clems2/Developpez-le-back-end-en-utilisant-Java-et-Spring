package com.chatop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
        // Test de lecture des variables (à supprimer après)
        System.out.println("Utilisateur BDD : " + System.getenv("DB_CHATOP_USER"));
	}

}
