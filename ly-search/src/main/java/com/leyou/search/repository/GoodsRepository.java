package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author：lidatu
 * @Date： 2019/01/25 星期五 23:14
 * @Description：
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {
}
