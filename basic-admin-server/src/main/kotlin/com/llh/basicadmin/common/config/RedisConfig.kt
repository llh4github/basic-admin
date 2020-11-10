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
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String, Any>()
        // 注入数据源
        redisTemplate.setConnectionFactory(redisConnectionFactory)
        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        val jackson2JsonRedisSerializer: Jackson2JsonRedisSerializer<*> = Jackson2JsonRedisSerializer(Any::class.java)
        val stringRedisSerializer = StringRedisSerializer()

        // key-value结构序列化数据结构

        redisTemplate.keySerializer = stringRedisSerializer
        redisTemplate.valueSerializer = jackson2JsonRedisSerializer
        // hash数据结构序列化方式,必须这样否则存hash 就是基于jdk序列化的
        redisTemplate.hashKeySerializer = stringRedisSerializer
        redisTemplate.hashValueSerializer = jackson2JsonRedisSerializer
        // 启用默认序列化方式
        redisTemplate.isEnableDefaultSerializer = true
        redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer)

        /// redisTemplate.afterPropertiesSet();

        /// redisTemplate.afterPropertiesSet();
        return redisTemplate
    }

    @Bean
    fun cacheManager(factory: RedisConnectionFactory): CacheManager {
        val stringRedisSerializer = StringRedisSerializer()
        val genericJackson2JsonRedisSerializer = GenericJackson2JsonRedisSerializer()
        // 配置序列化（解决乱码的问题）
        val config = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(timeToLive)
            .serializeKeysWith(RedisSerializationContext
                .SerializationPair
                .fromSerializer(stringRedisSerializer))
            .serializeValuesWith(RedisSerializationContext
                .SerializationPair
                .fromSerializer(genericJackson2JsonRedisSerializer))
            .disableCachingNullValues()

        return RedisCacheManager.builder(factory)
            .cacheDefaults(config)
            .build()
    }

    private fun jackson2JsonRedisSerializerConfig(): Jackson2JsonRedisSerializer<Any> {
        val jackson2JsonRedisSerializer = Jackson2JsonRedisSerializer(Any::class.java)
        val genericJackson2JsonRedisSerializer = GenericJackson2JsonRedisSerializer()
        val mapper = ObjectMapper()
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
//        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
//            ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.Id.CLASS)
        mapper.activateDefaultTypingAsProperty(LaissezFaireSubTypeValidator.instance,
            ObjectMapper.DefaultTyping.NON_FINAL, "@class")
        jackson2JsonRedisSerializer.setObjectMapper(mapper)
        return jackson2JsonRedisSerializer
    }
}
