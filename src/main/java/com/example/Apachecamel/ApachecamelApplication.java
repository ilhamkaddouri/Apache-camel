package com.example.Apachecamel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApachecamelApplication extends RouteBuilder{

	public static void main(String[] args) {
		SpringApplication.run(ApachecamelApplication.class, args);
	}

	@Override
	public void configure() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("let go");
		from("file:C:/Users/Ilham/Desktop/a").to("file:C:/Users/Ilham/Desktop/b");
		System.out.println("end");
		
	}

}
