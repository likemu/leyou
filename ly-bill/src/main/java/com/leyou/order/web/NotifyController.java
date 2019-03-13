package com.leyou.order.web;

import com.leyou.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：lidatu
 * @Date： 2019/02/11 星期一 21:06
 * @Description：
 */

@Slf4j
@RestController
@RequestMapping("notify")
public class NotifyController {

    @Autowired
    private OrderService orderService;

    /**
     * 微信支付的成功回调
     * @param result
     * @return
     */
    @PostMapping(value = "pay", produces = "application/xml")    //返回的类型为xml
    public Map<String, String> pay(@RequestBody Map<String, String> result){
        //处理回调
        orderService.handleNotify(result);

        log.info("[订单支付回调] 接收微信支付回调，结果：{}", result);
        //返回成功
        Map<String, String> msg = new HashMap<>();
        msg.put("return_code", "SUCCESS"); //return_code、return_msg 为微信回调中的返回字段
        msg.put("return_msg", "OK");
        return msg;
    }
}





















