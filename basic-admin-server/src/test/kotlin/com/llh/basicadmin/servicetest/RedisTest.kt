package com.llh.basicadmin.servicetest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.llh.basicadmin.common.util.JacksonUtils
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.service.sys.SysUserService
import org.junit.jupiter.api.Test
import org.ktorm.jackson.KtormModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate

/**
 *
 * Created At 2020/11/10 16:35
 *
 * @author llh
 */
@SpringBootTest
class RedisTest {

    @Autowired
    @Qualifier("redisTemplate")
    private lateinit var redisTemplateStr: StringRedisTemplate

    @Autowired
    private lateinit var sysUserService: SysUserService

    @Test
    fun testSave() {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(KtormModule())
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        val model = sysUserService.findById(1)

        val message = JacksonUtils.writeValueAsString(model)
        println(message)
        val m = JacksonUtils.readValue(message!!, SysUser::class.java)
        println(m)
        redisTemplateStr.opsForValue().set("aaa", message)
        val b = redisTemplateStr.opsForValue().get("aaa")
        val c = JacksonUtils.readValue(b, SysUser::class.java)
        println(c)
    }
}
