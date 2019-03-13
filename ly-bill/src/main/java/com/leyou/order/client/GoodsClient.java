package com.leyou.order.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author：lidatu
 * @Date： 2019/02/09 星期六 15:04
 * @Description：
 */

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

}
