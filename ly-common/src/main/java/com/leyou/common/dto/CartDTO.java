package com.leyou.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：lidatu
 * @Date： 2019/02/07 星期四 23:55
 * @Description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Long skuId; //商品skuId
    private Integer num; //购买数量
}









