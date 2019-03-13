package com.leyou.auth.web;

import com.leyou.auth.config.JwtProperties;
import com.leyou.auth.pojo.UserInfo;
import com.leyou.auth.service.AuthService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：lidatu
 * @Date： 2019/02/04 星期一 12:39
 * @Description：
 */

@RestController
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private JwtProperties prop;

    /**
     * 登录授权 返回值为token 登录一次之后 下次再登录也会带着token(后台需要) 需要保存到cookie中
     * @param username
     * @param password
     * @return
     */
    @PostMapping("login")
    public ResponseEntity<Void> login(
            @RequestParam("username") String username, @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response){
        //登录
        String token = authService.login(username, password);
        //写入cookie 调用工具类
//        Cookie cookie = new Cookie("LY_TOKEN", token);
//        response.addCookie(cookie);
//        cookie.set...
        //cookie：浏览器
        // httpOnly: 是否允许js操作cookie false允许 true不允许 避免跨站的js更新
        //request：domian域 防止其他网站访问
        CookieUtils.setCookie(request, response, prop.getCookieName(), token, prop.getCookieMaxAge(),null, true);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 检验用户登录状态
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN") String token,
                                           HttpServletRequest request, HttpServletResponse response){
//        if(StringUtils.isBlank(token)){ //不写也没问题 try代替
//            //如果没有token 证明未登录，返回403
//            throw new LyException(ExceptionEnum.UNAUTHORIZED);
//        }
        try {
            //解析token
            UserInfo info = JwtUtils.getInfoFromToken(token, prop.getPublicKey());

            //刷新token,重新生成token
            String newToken = JwtUtils.generateToken(info, prop.getPrivateKey(), prop.getExpire());
            //写入cookie
            CookieUtils.setCookie(request, response, prop.getCookieName(), newToken, prop.getCookieMaxAge(),null, true);
            //已登录，则返回用户信息
            return ResponseEntity.ok(info);
        }catch (Exception e){
            //token已过期，获取token被篡改
            throw new LyException(ExceptionEnum.UNAUTHORIZED);
        }
    }

}




















