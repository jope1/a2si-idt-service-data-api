package com.bjss.nhsd.a2si.idt.servicedataapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class IdtServiceDataApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdtServiceDataApiApplication.class, args);
	}
}
