package br.com.projeto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "br.com.entity")
@ComponentScan(basePackages = "br.com.controller")
@EnableJpaRepositories(basePackages = "br.com.repository")
@EnableTransactionManagement
public class SpringBootWebAlexApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebAlexApplication.class, args);
	}

}
