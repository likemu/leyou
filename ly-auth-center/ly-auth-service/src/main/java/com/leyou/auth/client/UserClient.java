package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author：lidatu
 * @Date： 2019/02/04 星期一 16:42
 * @Description：
 */

@FeignClient("user-service")
public interface UserClient extends UserApi {
}
