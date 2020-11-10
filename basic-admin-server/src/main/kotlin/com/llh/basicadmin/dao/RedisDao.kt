package com.llh.basicadmin.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 *
 * Created At 2020/11/10 16:27
 *
 * @author llh
 */
@Component
class RedisDao {
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    private val defaultTEL = 3000L // temp value

    private val timeUnit4Cache = TimeUnit.SECONDS

    /**  指定缓存失效时间 */
    fun expire(key: String, time: Long): Boolean {
        return try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS)
                return true
            }
            false
        } catch (e: Exception) {
            false
        }
    }

    /**  根据key 获取过期时间 */
    fun getExpire(key: String): Long? {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS)
    }

    /**  判断key是否存在 */
    fun hasKey(key: String): Boolean = try {
        redisTemplate.hasKey(key)
    } catch (e: Exception) {
        false
    }

    fun del(vararg keys: String): Long {
        if (keys.isEmpty()) return 0
        return if (keys.size == 1) {
            redisTemplate.delete(keys[0]).compareTo(false).toLong()
        } else {
            redisTemplate.delete(keys.toList())
        }

    }


    // ============================String=============================

    /**  缓存字符串 */
    fun set(key: String, value: Any, time: Long = defaultTEL): Boolean {
        return try {
            redisTemplate.opsForValue().set(key, value, time, timeUnit4Cache)
            true
        } catch (e: Exception) {
            false
        }
    }

    /** 获取字符串 */
    fun get(key: String): Any? {
        return redisTemplate.opsForValue().get(key)
    }

    /** 递增。[delta]递增因子必须大于0。非数字值的键不能递增。 */
    fun incr(key: String, delta: Long): Long? {
        if (delta < 0) {
            throw RuntimeException("递增因子必须大于0")
        }
        return redisTemplate.opsForValue().increment(key, delta)
    }

    /** 递减。[delta]递减因子必须大于0。非数字值的键不能递增。 */
    fun decr(key: String, delta: Long): Long? {
        if (delta < 0) {
            throw RuntimeException("递增因子必须大于0")
        }
        return redisTemplate.opsForValue().decrement(key, delta)
    }
    // ================================Map=================================

    /** 获取hash指定项目 */
    fun hget(key: String, item: String): Any? {
        return redisTemplate.opsForHash<String, Any?>().get(key, item)
    }

    /** 获取hashKey对应的所有键值 */
    fun hmget(key: String): MutableMap<String, Any?> {
        return redisTemplate.opsForHash<String, Any?>().entries(key)
    }

    /** 添加hash表数据 */
    fun hmset(key: String, map: Map<String, Any>, time: Long = defaultTEL): Boolean {
        return try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            if (time > 0) {
                expire(key, time)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** 向一张hash表中放入数据,如果不存在将创建 */
    fun hset(key: String, item: String, value: Any, time: Long = defaultTEL): Boolean {
        return try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            if (time > 0) {
                expire(key, time)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace();
            false;
        }
    }

    /** 删除hash表中的值 */
    fun hdel(key: String, vararg item: Any): Long {
        return redisTemplate.opsForHash<Any, Any>().delete(key, *item)
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    fun hHasKey(key: String, item: String): Boolean {
        return redisTemplate.opsForHash<Any, Any>().hasKey(key, item)
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    fun hIncr(key: String, item: String, delta: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, delta)
    }

    //============================set=============================

    /**
     * 根据key获取Set中的所有值
     * @param key 键
     */
    fun sGet(key: String): Set<Any>? {
        return redisTemplate.opsForSet().members(key)
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return  可能为空。 true 存在 false不存在
     */
    fun sHasKey(key: String, value: Any): Boolean? {
        return redisTemplate.opsForSet().isMember(key, value)
    }


    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数。可能为空。
     */
    fun sSet(key: String, vararg values: Any): Long? {
        return redisTemplate.opsForSet().add(key, *values)
    }
}
