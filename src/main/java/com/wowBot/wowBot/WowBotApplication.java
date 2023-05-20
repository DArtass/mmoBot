package com.wowBot.wowBot;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WowBotApplication {

	public static void main(String[] args) {
//		ApiContextInitializer.init();
//		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
//		try {
//			TelegramBot bot = new TelegramBot();
//			telegramBotsApi.registerBot(bot);
//		} catch (TelegramApiRequestException e) {
//			e.printStackTrace();
//		}

		OpenCV.loadLocally();
		SpringApplication.run(WowBotApplication.class, args);
	}

}
