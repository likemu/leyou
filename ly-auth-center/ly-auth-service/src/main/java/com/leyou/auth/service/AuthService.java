package com.leyou.auth.service;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.pojo.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * @Author：lidatu
 * @Date： 2019/02/04 星期一 12:42
 * @Description：
 */

@Slf4j
@Service
@EnableConfigurationProperties(JwtProperties.class)
public class AuthService {

    @Autowired
    private UserClient userClient;
    @Autowired
    private JwtProperties jwtProp;

    public String login(String username, String password) {
        try {
            //检验用户名和密码
            User user = userClient.queryUserByUsernameAndPassword(username, password);
            //判断
            if(user == null){
                throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
            }
            //生产token
            String token = JwtUtils.generateToken(new UserInfo(user.getId(), username), jwtProp.getPrivateKey(), jwtProp.getExpire());
            return token;
        }catch (Exception e){
            log.error("[授权中心] 用户名或密码不正确， 用户名称：{}", username, e);
            throw new LyException(ExceptionEnum.INVALID_USERNAME_PASSWORD);
        }
    }
}
















