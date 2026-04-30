package com.mateuslopes.SpringCoreDemo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringCoreDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context;

		context = SpringApplication.run(SpringCoreDemoApplication.class, args);

		Dev obj = context.getBean(Dev.class);

		obj.build();

	}

}
