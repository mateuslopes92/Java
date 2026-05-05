package com.mateuslopes.SpringBootDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBootDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context;

		context = SpringApplication.run(SpringBootDemoApplication.class, args);

		Dev obj = context.getBean(Dev.class);

		obj.build();

	}

}
