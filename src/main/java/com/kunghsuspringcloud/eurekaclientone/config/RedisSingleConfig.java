package com.kunghsuspringcloud.eurekaclientone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 为了复现redis.clients.jedis.exceptions.JedisConnectionException: Unexpected end of stream异常 问题用，
 * 排查完问题可留可不留
 * Redis单节点模式
 *
 * author:xuyaokun_kzx
 * date:2023/12/18
 * desc:
*/
@Configuration
public class RedisSingleConfig {

    private int maxTotal = 200;
    private int maxIdle = 100;
    private long maxWaitMillis = 10000;
    private boolean testOnBorrow = true;
    private boolean testOnReturn = true;
    private boolean testWhileIdle = true;

    /*
        timeout 设置为120s
        无Unexpected end of stream异常
     */
//    private int minIdle = 2;
//    private long minEvictableIdleTimeMillis = 15000;
//    private int numTestsPerEvictionRun = -1;
//    private long timeBetweenEvictionRunsMillis = 3000;

    /*
     * timeout 设置为10s
     * 无Unexpected end of stream异常
     */
//    private int minIdle = 2;
//    private long minEvictableIdleTimeMillis = 3000;*/
//    private int numTestsPerEvictionRun = -1;
//    private long timeBetweenEvictionRunsMillis = 2000;

    /*
     * timeout 设置为10s
     * 无Unexpected end of stream异常
     */
//    private int minIdle = 2;
//    private long minEvictableIdleTimeMillis = 3000;
//    private int numTestsPerEvictionRun = 2;
//    private long timeBetweenEvictionRunsMillis = 2000;

    /*
     * timeout 设置为10s
     * 有Unexpected end of stream异常
     */
//    private int minIdle = 20;
//    private long minEvictableIdleTimeMillis = 3000;
//    private int numTestsPerEvictionRun = 3;
//    private long timeBetweenEvictionRunsMillis = 2000;

    /*
     * timeout 设置为30s
     * 无Unexpected end of stream异常
     */
//    private int minIdle = 20;
//    private long minEvictableIdleTimeMillis = 3000;
//    private int numTestsPerEvictionRun = 2;
//    private long timeBetweenEvictionRunsMillis = 2000;

    /*
     * timeout 设置为30s
     * 有Unexpected end of stream异常
     */
//    private int minIdle = 40;
//    private long minEvictableIdleTimeMillis = 3000;
//    private int numTestsPerEvictionRun = 2;
//    private long timeBetweenEvictionRunsMillis = 2000;

    /*
        timeout 设置为2s
    */
    private int minIdle = 1;
    private long minEvictableIdleTimeMillis = 3000 * 1000;
    private int numTestsPerEvictionRun = -1;
    private long timeBetweenEvictionRunsMillis = 60000;

    @Bean
    public JedisPoolConfig getJedisPoolConfig(){

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis);
        poolConfig.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        poolConfig.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
        poolConfig.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        poolConfig.setTestOnBorrow(testOnBorrow);
        poolConfig.setTestOnReturn(testOnReturn);
        poolConfig.setTestWhileIdle(testWhileIdle);
        return poolConfig;
    }

    @Bean
    public JedisConnectionFactory getJedisConnectionFactory(){

        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(getJedisPoolConfig());
//        jedisConnectionFactory.setPassword(password);
//        jedisConnectionFactory.setHostName("127.0.0.1");//window版本
        jedisConnectionFactory.setHostName("192.168.91.128");//linux版本
        jedisConnectionFactory.setPort(6379);

        return jedisConnectionFactory;
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(getJedisConnectionFactory());
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(genericJackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(genericJackson2JsonRedisSerializer);
        return redisTemplate;
    }

}
