package com.leyou.page.mq;

import com.leyou.page.service.GoodsPageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：lidatu
 * @Date： 2019/02/01 星期五 14:37
 * @Description：
 */

@Component
public class ItemListener {

    @Autowired
    private GoodsPageService pageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.insert.queue", durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update", "item.saleAble", "item.unSaleAble"}
    ))
    public void listen(Long spuId){
        if(spuId == null){
            return;
        }
        //处理消息，对静态页进行创建
        pageService.createHtml(spuId);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.delete.queue", durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void listenDelete(Long spuId){
        if(spuId == null){
            return;
        }
        //处理消息，对静态页进行删除
        pageService.deleteHtml(spuId);
    }
}



















