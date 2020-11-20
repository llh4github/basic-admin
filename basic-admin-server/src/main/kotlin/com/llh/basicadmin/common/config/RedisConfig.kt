package com.llh.basicadmin.common.config


import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

/**
 *
 * Created At 2020/11/10 16:29
 *
 * @author llh
 */
@Configuration
@EnableCaching // 启用缓存，这个注解很重要；
// 继承CachingConfigurerSupport，为了自定义生成KEY的策略。可以不继承。
class RedisConfig : CachingConfigurerSupport() {
    @Value("\${spring.cache.redis.time-to-live}")
    private lateinit var timeToLive: Duration

    @Bean(name = ["redisTemplate"])
    fun redisTemplateStr(redisConnectionFactory: RedisConnectionFactory)
        : StringRedisTemplate {
        val redisTemplate = StringRedisTemplate()
        // 注入数据源
        redisTemplate.setConnectionFactory(redisConnectionFactory)
        return redisTemplate
    }


}
