package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by lidatu on 2019/01/09 10:56
 */

@EnableDiscoveryClient
@SpringBootApplication
public class LyUploadApplication {
	public static void main(String[] args) {
		SpringApplication.run(LyUploadApplication.class);
	}
}
