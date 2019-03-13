package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author：lidatu
 * @Date： 2019/02/02 星期六 16:03
 * @Description： 短信微服务 通过MQ 监听消息 短信工具类
 */

@SpringBootApplication
public class LySmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LySmsApplication.class, args);
    }
}
