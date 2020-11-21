package com.llh.basicadmin.servicetest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.llh.basicadmin.common.util.JacksonUtils
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.pojo.AccountInfo
import com.llh.basicadmin.service.sys.AccountService
import com.llh.basicadmin.service.sys.SysUserService
import org.junit.jupiter.api.Test
import org.ktorm.jackson.KtormModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
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

    @Autowired
    private lateinit var accountService: AccountService

    @Test
    fun testCacheFun() {
        accountService.loadUserByUsername("tom")
        println(" -------- ")
        val loadUserByUsername = accountService.loadUserByUsername("tom")
        println(loadUserByUsername.username)
        println(loadUserByUsername.authorities)
        val findById = sysUserService.findById(1)
        val a = setOf(1, 2)
        sysUserService.updateWithRoles(findById!!,a)
    }

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

    @Test
    fun testDeser() {
        val json = """
    {
  "id": 1,
  "username": "tom",
  "password": "11111111",
  "authorities": ["a","b"],
  "accountNonExpired": true,
  "credentialsNonExpired": true,
  "accountNonLocked": true,
  "enabled": true

    }
""".trimIndent()
        val readValue = JacksonUtils.readValue(json, AccountInfo::class.java)
        println(readValue.authorities)
    }
}
