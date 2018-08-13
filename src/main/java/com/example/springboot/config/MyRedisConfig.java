package com.example.springboot.config;



import com.example.springboot.bean.Department;
import com.example.springboot.bean.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;

@Configuration
public class MyRedisConfig {

    /****************************springboot 2.X 中设置 RedisCacheManager*******************************/
    //设置默认CacheManager还可以用此方法，但是这就是spring提供的默认设置，无法更改设置。
//    @Bean
//    public RedisCacheManager springDefaultCacheManager(RedisConnectionFactory factory) {
//        RedisCacheManager cacheManager = RedisCacheManager.create(factory);
//        return cacheManager;
//    }

    //@Primary设置为默认CacheManager
    @Primary
    @Bean
    public RedisCacheManager defaultCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

    //用于对象缓存的CacheManager
    @Bean
    public RedisCacheManager objectCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
                .entryTtl(Duration.ofHours(1)) // 设置缓存有效期一小时
                .disableCachingNullValues(); //设置不能缓存空值

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration).build();
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
    /****************************springboot 2.X 中设置 RedisCacheManager*******************************/

    @Bean
    public RedisTemplate<Object, Employee> empRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Employee> template = new RedisTemplate<Object, Employee>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Employee> ser = new Jackson2JsonRedisSerializer<Employee>(Employee.class);
        template.setDefaultSerializer(ser);
        return template;
    }
    @Bean
    public RedisTemplate<Object, Department> deptRedisTemplate(
            RedisConnectionFactory redisConnectionFactory)
            throws UnknownHostException {
        RedisTemplate<Object, Department> template = new RedisTemplate<Object, Department>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Department> ser = new Jackson2JsonRedisSerializer<Department>(Department.class);
        template.setDefaultSerializer(ser);
        return template;
    }

//  springboot 1.X 中这么设置
//    //CacheManagerCustomizers可以来定制缓存的一些规则
//    @Primary  //将某个缓存管理器作为默认的
//    @Bean
//    public RedisCacheManager employeeCacheManager(RedisTemplate<Object, Employee> empRedisTemplate){
//        RedisCacheManager cacheManager = new RedisCacheManager(empRedisTemplate);
//        //key多了一个前缀
//
//        //使用前缀，默认会将CacheName作为key的前缀
//        cacheManager.setUsePrefix(true);
//
//        return cacheManager;
//    }
//
//    @Bean
//    public RedisCacheManager deptCacheManager(RedisTemplate<Object, Department> deptRedisTemplate){
//        RedisCacheManager cacheManager = new RedisCacheManager(deptRedisTemplate);
//        //key多了一个前缀
//
//        //使用前缀，默认会将CacheName作为key的前缀
//        cacheManager.setUsePrefix(true);
//
//        return cacheManager;
//    }


}
