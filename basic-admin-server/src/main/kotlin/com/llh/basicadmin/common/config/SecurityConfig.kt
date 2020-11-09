package com.llh.basicadmin.common.config


import com.llh.basicadmin.common.util.PwdUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder

/**
 *
 * Created At 2020/11/9 16:42
 *
 * @author llh
 */
@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        http {
            authorizeRequests {
                authorize("/**", permitAll) // 先放行所有
            }
        }
    }

    /**
     * 密码加密
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        // 为工具类注入实例值
        PwdUtil.setPasswordEncoder(encoder)
        return encoder
    }


}
