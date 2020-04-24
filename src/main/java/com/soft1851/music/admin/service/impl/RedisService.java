//package com.soft1851.music.admin.service.impl;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
///**
// * @Description TODO
// * @Author 涛涛
// * @Date 2020/4/21 13:14
// * @Version 1.0
// **/
//@Service
//@Slf4j
//public class RedisService {
////
////    /**
////     * RedisTemplate默认的序列化方案是JdkSeriallizationRedisSerizalizer
////     * StringRedisTemplate中,key的默认序列化方案是StringRedisSerializer
////     */
////    @Resource
////    private RedisTemplate redisTemplate;
////
////    @Resource
////    private StringRedisTemplate stringRedisTemplate;
////    public void testRedis(){
////        ValueOperations<String, String> ops = redisTemplate.opsForValue();
//////        ops.set("niit", "soft");
//////        ValueOperations<String, String> ops2 = stringRedisTemplate.opsForValue();
//////        ops.set("ntt", "soft1851");
////        System.out.println(ops.get("niit"));
//////        System.out.println(ops2.get("ntt"));
////    }
////
////    /**
////     * 验证码存放
////     * @param key 键
////     * @param value 值
////     * @param duration 有效时长
////     */
////    public void save(String key,String value,long duration){
////        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
////        ops.set(key,value,duration);
////        log.info("验证码存储在Redis中"+ops.get(key));
////    }
////
////    public String get(String key) {
////        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
////
////        log.info("*********////**********");
////        log.info(key);
////        log.info("*********////**********");
////        String value = ops.get(key);
////        if (value != null) {
////            return value;
////        } else {
////            return "";
////        }
////    }
////
////    public void set(String key,String value ,int time) {
////        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
////        ops.set(key,value,time);
////    }
//}
