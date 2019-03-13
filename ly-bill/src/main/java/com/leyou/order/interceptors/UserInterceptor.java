package com.leyou.order.interceptors;

import com.leyou.auth.pojo.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import com.leyou.order.config.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author：lidatu
 * @Date： 2019/02/05 星期二 18:50
 * @Description：
 */

@Slf4j
//@EnableConfigurationProperties(JwtProperties.class)
public class UserInterceptor implements HandlerInterceptor {
    //@Autowired
    private JwtProperties prop;

    // 定义一个线程域，存放登录用户
    private static final ThreadLocal<UserInfo> tl = new ThreadLocal<>();

    public UserInterceptor(JwtProperties prop) {
        this.prop = prop;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie
        String token = CookieUtils.getCookieValue(request, prop.getCookieName());
        try {
            //解析token
            UserInfo user = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            //传递user 传递到controller才进行操作
            tl.set(user);
            //request.setAttribute("user", user);
            //放行
            return true;
        }catch (Exception e){
            log.error("[购物车服务] 解析用户身份失败", e);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //最后用完数据,一定要清空
        tl.remove();
    }

    public static UserInfo getUser() {
        return tl.get();
    }

}
