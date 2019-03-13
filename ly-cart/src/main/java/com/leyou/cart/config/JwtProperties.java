package com.leyou.cart.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.security.PublicKey;

/**
 * @Author：lidatu
 * @Date： 2019/02/05 星期二 18:45
 * @Description：
 */

@Data
@ConfigurationProperties(prefix = "ly.jwt")
public class JwtProperties {

    private String pubKeyPath; // 公钥路径

    private PublicKey publicKey; // 公钥

    private String cookieName;

    @PostConstruct
    public void init(){ //对象创建完成，变量初始化以后执行，保证公钥私钥有值
        try {
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
