package com.leyou.gateway.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
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
    private String pubKeyPath; // 公钥路径
    private String cookieName; //cookie名称

    private PublicKey publicKey;  // 公钥

    @PostConstruct
    public void init(){ //对象创建完成，变量初始化以后执行，保证公钥私钥有值
        try {
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}















