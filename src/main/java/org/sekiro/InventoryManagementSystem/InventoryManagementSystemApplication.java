package org.sekiro.InventoryManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class InventoryManagementSystemApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(InventoryManagementSystemApplication.class, args);
		String [] beans = context.getBeanDefinitionNames();
//		Arrays.stream(beans).sorted().forEach(System.out::println);
	}

	@Bean
	public String helloBean() {
		return "hello";
	}

}
