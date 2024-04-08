package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author HLxxx
 * @version 1.0
 */
@Configuration
@Slf4j
public class RedisConfiguration {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        log.info("Redisのテンプレートオブジェクトを作成します");
        RedisTemplate redisTemplate = new RedisTemplate();
        //Redisの接続ファクトリーオブジェクトを設定します
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //Redisのキーのシリアライザーを設定します。
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
