package com.leyou.search.repository;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.search.client.GoodsClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author：lidatu
 * @Date： 2019/01/25 星期五 23:21
 * @Description：
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsRepositoryTest {

    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private ElasticsearchTemplate template;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SearchService searchService;

    @Test
    public void testCreateIndex(){
        template.createIndex(Goods.class);
        template.putMapping(Goods.class);
    }
    @Test
    public void loadData() {
        int page = 1;
        int size = 0;
        int rows = 100;
        do{
            // 查询spu信息
            PageResult<Spu> result = goodsClient.querySpuByPage(page, rows,true, null);
            List<Spu> spuList = result.getItems();//当前页结构 数据
            if(CollectionUtils.isEmpty(spuList)){
                break;
            }
            // 构建成goods
            List<Goods> goodList = spuList.stream()
                    .map(searchService::buildGoods).collect(Collectors.toList());
            size = spuList.size();
            //存入索引库
            goodsRepository.saveAll(goodList);
            page++;
        } while (size == 100);
    }

}