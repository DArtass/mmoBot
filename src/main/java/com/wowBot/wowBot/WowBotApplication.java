package com.wowBot.wowBot;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WowBotApplication {
	public static void main(String[] args) {
		OpenCV.loadLocally();
		SpringApplication.run(WowBotApplication.class, args);
	}
}
