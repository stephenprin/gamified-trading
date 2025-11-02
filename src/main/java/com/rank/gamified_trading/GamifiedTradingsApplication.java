package com.rank.gamified_trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GamifiedTradingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GamifiedTradingsApplication.class, args);
	}

}
