package com.leyou.auth.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @Author：lidatu
 * @Date： 2019/02/04 星期一 12:20
 * @Description：
 */

@Data
@Slf4j
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties {
    private String secret;     // 密钥
    private String pubKeyPath; // 公钥路径
    private String priKeyPath; // 私钥路径
    private int expire;       // token过期时间

    private String cookieName; //cookie名称
    private int cookieMaxAge;  //cookie的过期时间

    private PublicKey publicKey;  // 公钥
    private PrivateKey privateKey;  // 私钥

    //对象(类X)一旦实例化后(加载)，就应该读取公钥和私钥
    //@PostConstruct：spring->bean初始化周期，
    // 构造函数执行完毕以后执行@PostConstruct注解，例如<bean id="" class="" init=""> init=""等同于这个注解
    @PostConstruct
    public void init(){ //对象创建完成，变量初始化以后执行，保证公钥私钥有值
        try {
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);
            if (!pubKey.exists() || !priKey.exists()) {
                // 生成公钥和私钥，如果不存在，需生成
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            // 获取(读取)并保存(成员变量里面)公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException();
        }
    }
}















