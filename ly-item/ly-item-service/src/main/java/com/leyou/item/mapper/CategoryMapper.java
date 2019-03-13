package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by lidatu on 2019/01/08 09:05
 * IdListMapper：查询一堆id/删除一堆id
 */

public interface CategoryMapper extends Mapper<Category> , IdListMapper<Category, Long> {

    /**
     * 根据品牌id查询商品分类 点击修改品牌 回显数据
     * @param bid
     * @return
     */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryByBrandId(Long bid);
}
