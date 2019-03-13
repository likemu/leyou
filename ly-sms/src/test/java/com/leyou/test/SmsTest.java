package com.leyou.test;

import com.leyou.sms.utils.SmsUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * @Author：lidatu
 * @Date： 2019/02/02 星期六 18:59
 * @Description：
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private SmsUtils smsUtils;

    @Test
    public void testSend() throws InterruptedException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("phone", "17344579204");
        msg.put("code", "543210");
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code", msg);

        Thread.sleep(1000L);

    }

}


























