package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author：lidatu
 * @Date： 2019/02/04 星期一 16:37
 * @Description：
 */

public interface UserApi {

    @GetMapping("query")
    User queryUserByUsernameAndPassword(
            @RequestParam("username") String username,
            @RequestParam("password") String password);

}















