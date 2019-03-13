package com.leyou.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by lidatu on 2019/01/03 14:35
 */
//@SpringCloudApplication：自带三个（@SpringBootApplication、@EnableDiscoveryClient、熔断器）

@EnableZuulProxy
@SpringCloudApplication
public class LyGateway {
	public static void main(String[] args) {
		SpringApplication.run(LyGateway.class);
	}
}
