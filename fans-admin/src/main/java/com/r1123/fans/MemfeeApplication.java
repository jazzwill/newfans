package com.r1123.fans;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = { "com.r1123.fans.admin", "com.r1123.fans.core" })
public class MemfeeApplication {

	public static void main(String[] args) {

		SpringApplication.run(MemfeeApplication.class, args);
	}
}
