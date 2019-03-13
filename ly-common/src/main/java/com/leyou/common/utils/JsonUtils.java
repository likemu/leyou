package com.leyou.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 对对象进行SoN的序列化或者反序列化
 * Created by lidatu on 2019/01/03 18:53
 **/

@Slf4j
public class JsonUtils {

    public static final ObjectMapper mapper = new ObjectMapper();

    //private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    @Nullable
    public static String serialize(Object obj) {//序列化：把对象转成字符串（json）
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    @Nullable
    public static <T> T parse(String json, Class<T> tClass) {//反序列化：把字符串（json）转成对象
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <E> List<E> parseList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }

    @Nullable
    public static <T> T nativeRead(String json, TypeReference<T> type) {
        try {
            return mapper.readValue(json, type);
        } catch (IOException e) {
            log.error("json解析出错：" + json, e);
            return null;
        }
    }



    //举例子：
    /*@Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User{
        String name;
        Integer age;
    }

    public static void main(String[] args) {
        User user = new User("jack",23);
        //序列化
        String json = serialize(user);
        System.out.println("json = "+json);//json = {"name":"jack","age":23}
        //反序列化
        User user1 = parse(json, User.class);
        System.out.println(user1);//JsonUtils.User(name=jack, age=23)

        //数组序列化成集合
        String json1 = "[29,-19,8,38]";
        List<Integer> list = parseList(json1, Integer.class);
        System.out.println("List = "+list);//List = [29, -19, 8, 38]

        //对象序列化成Map
        //language=JSON
        String json2 = "{\"name\": \"jack\",\"age\": 24}"; //快捷键alt+enter:选择Inject... 再alt+enter:选edit... 选JSON 在控制板输入{"name": "jack","age": 24} 自动转带"\"的json
        Map<String, String> map = toMap(json, String.class, String.class);
        System.out.println("map = " + map);//map = {name=jack, age=23}

        //复杂类型转换（难点）
        //List中装Map类型
        String json3 = "[{\"name\": \"jack\",\"age\": 24},{\"name\": \"Rose\",\"age\": 23}]";
        //TypeReference类型引用  new的是匿名内部类（必须）
        List<Map<String, String>> mapList = nativeRead(json3, new TypeReference<List<Map<String, String>>>() {
        });
        for (Map<String, String> map1 : mapList) {
            System.out.println("stringMap = " + map1);//stringMap = {name=jack, age=24} stringMap = {name=Rose, age=23}
        }
    }*/

}
