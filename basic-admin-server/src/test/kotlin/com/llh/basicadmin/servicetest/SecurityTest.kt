package com.llh.basicadmin.servicetest

import com.llh.basicadmin.common.util.JwtTokenUtil
import com.llh.basicadmin.common.util.PwdUtil
import com.llh.basicadmin.pojo.AccountInfo
import com.llh.basicadmin.service.sys.SysUserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

/**
 *
 * Created At 2020/11/9 16:55
 *
 * @author llh
 */
@SpringBootTest
class SecurityTest {
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var sysUserService: SysUserService

    @Test
    fun testHash() {
        val hash = passwordEncoder.encode("llh")
        val h2 = PwdUtil.encode("llh")

        println("$hash \n$h2")
    }

    @Test
    fun testAccountInfo() {
        val model = sysUserService.findById(1)
        val info = AccountInfo(model!!)
        println(info)
    }

    @Test
    fun testConfig() {
        println(JwtTokenUtil.config.secretKey)
    }

    @Test
    fun testTokenGen() {
        val m = mutableMapOf<String, Any>()

        m["fff"] = "CCCC"
        val generateToken = JwtTokenUtil.generateToken("accc", m, 12)
        val token = JwtTokenUtil.generateAccessToken("Tom", m)
        println(generateToken)
        println(token)
    }
}
