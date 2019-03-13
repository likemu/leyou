package com.leyou.page.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author：lidatu
 * @Date： 2019/01/25 星期五 20:53
 * @Description：
 */

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {
//    @GetMapping("category/list/ids")
//    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
