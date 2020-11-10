package com.llh.basicadmin.servicetest

import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.pojo.AccountInfo
import com.llh.basicadmin.service.sys.SysUserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate

/**
 *
 * Created At 2020/11/10 16:35
 *
 * @author llh
 */
@SpringBootTest
class RedisTest {
    @Autowired
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Autowired
    private lateinit var sysUserService: SysUserService

    @Test
    fun testSave() {
        val model = sysUserService.findById(1)
        redisTemplate.opsForValue().set("aaa", model!!)
    }
}
