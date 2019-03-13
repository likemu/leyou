package com.leyou.page.web;

import com.leyou.page.service.GoodsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @Author：lidatu
 * @Date： 2019/01/31 星期四 12:48
 * @Description：
 */

@Controller
public class GoodsPageController {

    @Autowired
    private GoodsPageService goodsPageService;
    /**
     * @param model
     * @param spuId
     * @return
     */
    @GetMapping("item/{id}.html")
    public String toItemPage(Model model, @PathVariable("id")Long spuId){
        //查询数据模型
        Map<String, Object> attributes = goodsPageService.loadModel(spuId);
        //准备模型数据
        model.addAllAttributes(attributes);
        //返回试图
        return "item";    // 跳转到商品详情页 使用thymeleaf技术  可以是freemark ...
    }

}
