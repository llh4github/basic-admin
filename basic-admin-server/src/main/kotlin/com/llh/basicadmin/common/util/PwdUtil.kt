package com.llh.basicadmin.common.util

import org.springframework.security.crypto.password.PasswordEncoder

/**
 * 密码工具类。
 *
 * 写成“静态”方法，调用方便
 * Created At 2020/11/9 16:58
 *
 * @author llh
 */
object PwdUtil {
    private lateinit var passwordEncoder: PasswordEncoder

    internal fun setPasswordEncoder(encoder: PasswordEncoder) {
        this.passwordEncoder = encoder
    }


    fun encode(rawPwd: String): String {
        return passwordEncoder.encode(rawPwd)
    }

    fun match(rawPwd: String, hashedPwd: String): Boolean {
        return passwordEncoder.matches(rawPwd, hashedPwd)
    }
}
