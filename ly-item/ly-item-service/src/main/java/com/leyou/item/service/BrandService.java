package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by lidatu on 2019/01/08 20:14
 */

@Service
public class BrandService {

    @Autowired
    private BrandMapper bandMapper;

    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //开始分页 利用mybatis拦截器 对接下来执行的sql语句 自动拼接上limit
        PageHelper.startPage(page, rows);//当前页号和每页页数
        //搜索字段过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNotBlank(key)) {
            //过滤条件 --名称为name属性或者letter属性 类似模糊查询
            example.createCriteria().orLike("name", "%" + key + "%")
                    .orEqualTo("letter", key.toUpperCase()); //转成大写字母
        }
        //排序
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");//查询降序子句  三目运算
            example.setOrderByClause(orderByClause);
        }
        //查询
        List<Brand> list = bandMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        //解析分页结果
        PageInfo<Brand> info = new PageInfo<>(list);
        return new PageResult<>(info.getTotal(), list);//总条数 当前页数据
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增品牌
        brand.setId(null);
        int count = bandMapper.insert(brand); //insertSelective:新增非空的字段  insert：新增所有字段
        if (count != 1) {
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }
        //新增中间表 没有实体类 如何新增？ 没有通用mapper id不止一个 要新增多次
        for (Long cid : cids) {
            count = bandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1) {
                throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }
    }

    /**
     * 修改品牌 连分类一起修改 判断中间表是否有brand_id 有则先删除 再添加
     * @param brand
     */
    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        if (brand.getId() == null) {
            throw new LyException(ExceptionEnum.BRAND_ID_CANNOT_BE_NULL);
        }
        int count = bandMapper.updateByPrimaryKey(brand);//实现修改
        if (count != 1) {
            throw new LyException(ExceptionEnum.BRAND_UPDATE_ERROR);
        }
        bandMapper.deleteBrandId(brand.getId());//先删除 否则出现重复添加主键的错误
        for (Long cid : cids) {
            count = bandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1) {
                throw new LyException(ExceptionEnum.BRAND_UPDATE_ERROR);
            }
        }

    }

    /**
     * 通过id查询品牌--商品列表Goods need
     *
     * @param id
     * @return
     */
    public Brand queryById(Long id) {
        Brand brand = bandMapper.selectByPrimaryKey(id);
        if (brand == null) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brand;
    }

    public List<Brand> queryBrandByCategoryId(Long cid) {
        List<Brand> brands = bandMapper.queryBrandByCategoryId(cid);
        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brands;
    }

    /**
     * 删除品牌
     * @param id
     */
    public void deleteBrand(Long id) {
        int count = bandMapper.deleteByPrimaryKey(id);
        int i = bandMapper.deleteBrandId(id);
        if(i != 1 & count != 1){
            throw new LyException(ExceptionEnum.BRAND_DELETE_ERROR);
        }
    }

    /**
     * 聚合品牌 通过一堆id查询品牌 - 搜索
     * @param ids
     * @return
     */
    public List<Brand> queryBrandByIds(List<Long> ids) {
        List<Brand> brands = bandMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(brands)){
            throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
        }
        return brands;
    }

}
