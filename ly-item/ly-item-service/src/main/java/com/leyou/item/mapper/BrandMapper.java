package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by lidatu on 2019/01/08 20:13
 */

public interface BrandMapper extends BaseMapper<Brand> {
	/**
	 * 新增商品分类和品牌中间表数据
	 * @param cid 商品分类id
	 * @param bid 品牌id
	 * @return
	 */
	@Insert("INSERT INTO tb_category_brand (category_id, brand_id) VALUES (#{cid},#{bid})")
	int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);

	@Select("SELECT b.* FROM tb_category_brand cb LEFT JOIN tb_brand b ON cb.brand_id = b.id WHERE cb.category_id = #{cid}")
    List<Brand> queryBrandByCategoryId(@Param("cid") Long cid);

	/**
	 * 删除中间表的所有bid数据
	 * @param bid
	 * @return
	 */
	@Delete("DELETE FROM tb_category_brand WHERE brand_id=#{bid}")
	int deleteBrandId(@Param("bid") Long bid);
}
