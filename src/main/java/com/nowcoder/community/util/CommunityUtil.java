package com.nowcoder.community.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//不用容器托管，方法比较简单，选择静态方法直接调用
public class CommunityUtil {

//    生成随机字符串（激活码），上传文件时为其生成名字也是随机字符串
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("_", ""); //JAVA自带的方法，生成字母数字以及下划线，这里选择不用下划线，替代为空字符
    }

//    MD5加密，即对用户的密码加密
//    MD5加密特点：只能加密不能解密，会将字符串每次都加密成同样的密文，只用MD5加密安全性不够高
//    通常处理方式：密码+随机字符串 -> 加密
    public static String md5(String key) { //这里的key就是密码+随机字符串
        if (StringUtils.isBlank(key))  //maven导入的包，专门用来判空：空格、null、空字符串都认为是空
            return null;
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    public static String getJSONString(int code, String msg, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        if (map != null) {
            for (String key : map.keySet()) {
                json.put(key, map.get(key));
            }
        }
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        return getJSONString(code, msg, null);
    }

    public static String getJSONString(int code) {
        return getJSONString(code, null, null);
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 25);
        System.out.println(getJSONString(0, "ok", map));
    }
}
