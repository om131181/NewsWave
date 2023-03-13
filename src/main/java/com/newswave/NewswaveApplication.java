package com.newswave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan
public class NewswaveApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(NewswaveApplication.class, args);
		
		System.out.println(context.getBeanDefinitionCount());
	}

}
