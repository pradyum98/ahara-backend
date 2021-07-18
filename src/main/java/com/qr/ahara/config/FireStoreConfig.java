package com.qr.ahara.config;

import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;

@Configuration
public class FireStoreConfig {
	@Bean
	public Firestore getFireStore() throws IOException {
		final ClassLoader classLoader = getClass().getClassLoader();

//		var serviceAccount = new FileInputStream(credentialPath);
		var serviceAccount = classLoader.getResourceAsStream("service-account-credentials.json");
		var credentials = GoogleCredentials.fromStream(serviceAccount);

		var options = FirestoreOptions.newBuilder()
						.setCredentials(credentials).build();

		return options.getService();
	}
}
