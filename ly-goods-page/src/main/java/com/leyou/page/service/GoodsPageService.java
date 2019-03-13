package com.leyou.page.service;

import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：lidatu
 * @Date： 2019/01/31 星期四 15:39
 * @Description：
 */

@Slf4j
@Service
public class GoodsPageService {

    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private SpecificationClient specificationClient;
    @Autowired
    private TemplateEngine templateEngine;

    public Map<String, Object> loadModel(Long id) {
        try {
            // 模型数据
            Map<String, Object> modelMap = new HashMap<>();
            // 查询spu
            Spu spu = goodsClient.querySpuById(id);
            // 查询spuDetail
            SpuDetail detail = spu.getSpuDetail();
            // 查询sku
            List<Sku> skus = spu.getSkus();
            // 准备商品分类
            List<Category> categories = categoryClient.queryCategoryByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            // 准备品牌数据
            Brand brand = brandClient.queryBrandById(spu.getBrandId());
            // 查询规格组及组内参数
            List<SpecGroup> groups = specificationClient.queryGroupByCid(spu.getCid3());
            // 查询商品分类下的特有规格参数
            List<SpecParam> params =
                    specificationClient.querySpecParamList(null, spu.getCid3(), null);
            // 处理成id:name格式的键值对
            Map<Long,String> paramMap = new HashMap<>();
            for (SpecParam param : params) {
                paramMap.put(param.getId(), param.getName());
            }

            // 装填模型数据
            modelMap.put("spu", spu);
            modelMap.put("spuDetail", detail);
            modelMap.put("skus", skus);
            modelMap.put("categories", categories);
            modelMap.put("brand", brand);
            modelMap.put("groups", groups);
            modelMap.put("paramMap", paramMap);

            return modelMap;

        } catch (Exception e) {
            log.error("加载商品数据出错,spuId:{}", id, e);
        }
        return null;
    }

    //创建静态页面 此处上传到本地 实际上需要上传到nginx中 配置文件设置 在nginx html中找不到再到数据中加载
    public void createHtml(Long spuId){
        //上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        //输出流
        File dest = new File("D:\\upload", spuId + ".html");//本地路径

        if(dest.exists()){ dest.delete(); }

        try(PrintWriter writer = new PrintWriter(dest, "UTF-8")) {
            templateEngine.process("item", context, writer);//item.html模板 生产--------
        }catch (Exception e){
            log.error("[静态页服务] 生成静态页异常！", e);
        }

    }

    public void deleteHtml(Long spuId) {
        File dest = new File("D:\\upload", spuId + ".html");//本地路径
        if(dest.exists()){ dest.delete(); }

    }





     /*@Autowired
    private BrandClient brandClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private SpecificationClient specificationClient;
    public Map<String, Object> loadModel(Long spuId) {
        Map<String, Object> model = new HashMap<>();
        //查询spu
        Spu spu = goodsClient.querySpuById(spuId);
        //查询skus
        List<Sku> skus = spu.getSkus();
        //查询详情
        SpuDetail detail = spu.getSpuDetail();
        //查询brand
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        //查询商品的分类
        List<Category> categories = categoryClient.queryCategoryByIds(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        //查询规格参数
        List<SpecGroup> specs = specificationClient.queryGroupByCid(spu.getCid3());

        model.put("title", spu.getTitle());
        model.put("subTitle", spu.getSubTitle());
        model.put("spu", spu);
        model.put("skus", skus);
        model.put("spuDetail", detail);
        model.put("brand", brand);
        model.put("categories", categories);
        model.put("specs", specs);
        return model;
    }*/

}
