package io.b1ruk.start.whatsAppS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WhatsAppSApplication {

	public static void main(String[] args) {
		SpringApplication.run(WhatsAppSApplication.class, args);
	}

}
