package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lidatu on 2019/01/08 09:06
 */

@Service
public class CategoryService {

	@Autowired
	private CategoryMapper categoryMapper;

	public List<Category> queryCategoryListByPid(Long pid) {
		//查询条件 mapper会把对象中的非空属性作为查询条件
		Category t = new Category();
		t.setParentId(pid);
		List<Category> list = categoryMapper.select(t);
		//判断查询结果
		if(CollectionUtils.isEmpty(list)){
			throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);//失败返回404
		}
		return list;
	}

	/**
	 * 根据一堆id查询
	 * @param ids
	 * @return
	 */
	public List<Category> queryByIds(List<Long> ids) {
		List<Category> list = categoryMapper.selectByIdList(ids);
		if (CollectionUtils.isEmpty(list)) {
			throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
		}
		return list;
	}

	/**
	 * 根据品牌id查询分类
	 * @param bid
	 * @return
	 */
	public List<Category> queryByBrandIds(Long bid) {
		List<Category> categories = categoryMapper.queryByBrandId(bid);
		if(CollectionUtils.isEmpty(categories)){
			throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
		}
		return categories;
	}

	/**
	 * 根据3级分类id，查询1~3级的分类  展示到面包屑
	 * @param id
	 * @return
	 */
	public List<Category> queryAllByCid3(Long id) {
		Category c3 = this.categoryMapper.selectByPrimaryKey(id);
		Category c2 = this.categoryMapper.selectByPrimaryKey(c3.getParentId());
		Category c1 = this.categoryMapper.selectByPrimaryKey(c2.getParentId());
		return Arrays.asList(c1,c2,c3);
	}
}
