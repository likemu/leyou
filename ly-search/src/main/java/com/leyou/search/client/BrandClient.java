package com.leyou.search.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author：lidatu
 * @Date： 2019/01/25 星期五 23:10
 * @Description：
 */

@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}
