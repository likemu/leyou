package com.leyou.page.client;

import com.leyou.item.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author：lidatu
 * @Date： 2019/01/25 星期五 23:08
 * @Description：
 */

@FeignClient("item-service")
public interface SpecificationClient extends SpecificationApi {
}
