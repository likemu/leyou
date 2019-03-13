package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by lidatu on 2019/01/03 14:30
 */

@EnableEurekaServer
@SpringBootApplication
public class LyRegistry {
	public static void main(String[] args) {
		SpringApplication.run(LyRegistry.class);
	}
}
