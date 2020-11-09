package com.llh.basicadmin.servicetest

import com.llh.basicadmin.common.util.PwdUtil
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

    @Test
    fun testHash() {
        val hash = passwordEncoder.encode("llh")
        val h2 = PwdUtil.encode("llh")

        println("$hash \n$h2")
    }
}
