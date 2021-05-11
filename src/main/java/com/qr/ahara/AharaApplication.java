package com.qr.ahara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AharaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AharaApplication.class, args);
		System.out.println("GCP CLoud Run App!");
	}

}
