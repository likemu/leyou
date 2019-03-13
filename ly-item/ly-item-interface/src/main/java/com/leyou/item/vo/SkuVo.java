package com.leyou.item.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：lidatu
 * @Date： 2019/01/25 星期五 22:30
 * @Description：
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuVo {
    private Long id;
    private String title;
    private Long price;
    private String image;
}
