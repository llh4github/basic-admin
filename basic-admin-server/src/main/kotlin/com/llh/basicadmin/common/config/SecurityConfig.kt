package com.llh.basicadmin.common.config


import com.llh.basicadmin.common.util.PwdUtil
import com.llh.basicadmin.service.sys.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
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
    @Autowired
    private lateinit var accountService: AccountService

    override fun configure(http: HttpSecurity) {
        http {
            csrf {
                disable() // 取消表单请求跨域保护
            }
            authorizeRequests {
                authorize("/**", permitAll) // 先放行所有
            }
        }
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth
            ?.userDetailsService(accountService)
            ?.passwordEncoder(passwordEncoder())
        super.configure(auth)
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
