package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import com.leyou.item.api.GoodsApi;
import com.leyou.item.pojo.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author：lidatu
 * @Date： 2019/01/25 星期五 20:53
 * @Description：
 */

@FeignClient("item-service")
public interface CategoryClient extends CategoryApi {

}
