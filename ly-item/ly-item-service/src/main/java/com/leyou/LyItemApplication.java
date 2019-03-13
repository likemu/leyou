package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Created by lidatu on 2019/01/03 14:53
 */
//@EnableDiscoveryClient 注册到eureka中

@EnableDiscoveryClient
@SpringBootApplication
//导入 tk
@MapperScan("com.leyou.item.mapper")
public class LyItemApplication {
	public static void main(String[] args) {
		SpringApplication.run(LyItemApplication.class);
	}
}
