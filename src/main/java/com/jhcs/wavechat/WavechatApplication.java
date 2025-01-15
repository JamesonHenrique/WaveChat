package com.jhcs.wavechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WavechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(WavechatApplication.class, args);
	}

}
