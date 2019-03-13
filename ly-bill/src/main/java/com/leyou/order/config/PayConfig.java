package com.leyou.order.config;

import com.github.wxpay.sdk.WXPayConfig;
import lombok.Data;

import java.io.InputStream;

/**
 * @Author：lidatu
 * @Date： 2019/02/11 星期一 12:42
 * @Description：
 */

@Data
public class PayConfig implements WXPayConfig {

    private String appID; //公众号ID
    private String mchID; //商户ID
    private String key; //生成签名的密钥
    private int httpConnectTimeoutMs; //连接超时时间
    private int httpReadTimeoutMs; // 读取超时时间
    private String notifyUrl; //下单通知回调地址

    @Override
    public InputStream getCertStream() {
        return null;
    }

}













