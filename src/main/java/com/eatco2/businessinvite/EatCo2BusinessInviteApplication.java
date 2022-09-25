package com.eatco2.businessinvite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/**
 * @author Rajashekara
 *
 */
@EnableEncryptableProperties 
@SpringBootApplication 
public class EatCo2BusinessInviteApplication {
	public static void main(String[] args) {
		SpringApplication.run(EatCo2BusinessInviteApplication.class, args);
	}

}
