package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author：lidatu
 * @Date： 2019/01/31 星期四 12:09
 * @Description： 查询商品详情
 */

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class LyGoodsPageApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyGoodsPageApplication.class, args);
    }
}
