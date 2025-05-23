package org.sekiro.InventoryManagementSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;
import java.util.Comparator;

@SpringBootApplication
public class InventoryManagementSystemApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementSystemApplication.class, args);

//		String [] beanNames = context.getBeanDefinitionNames();
//		Arrays.sort(beanNames);
//		Arrays.asList(beanNames).stream().forEach(System.out::println);
	}

}
