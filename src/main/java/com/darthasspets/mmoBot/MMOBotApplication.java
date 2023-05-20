package com.darthasspets.mmoBot;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MMOBotApplication {
	public static void main(String[] args) {
		OpenCV.loadLocally();
		SpringApplication.run(MMOBotApplication.class, args);
	}
}
