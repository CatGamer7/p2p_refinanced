package com.finance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class }) //Remove after PoC
public class P2pFinanceFriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(P2pFinanceFriendApplication.class, args);
	}

}
