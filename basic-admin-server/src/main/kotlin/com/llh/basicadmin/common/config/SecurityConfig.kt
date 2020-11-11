package com.llh.basicadmin.common.config


import com.fasterxml.jackson.databind.ObjectMapper
import com.llh.basicadmin.common.exception.code.AuthError
import com.llh.basicadmin.common.util.JwtConfig
import com.llh.basicadmin.common.util.JwtTokenUtil
import com.llh.basicadmin.common.util.PwdUtil
import com.llh.basicadmin.service.sys.AccountService
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

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

    @Autowired
    private lateinit var jwtTokenFilter: JwtAuthenticationTokenFilter

    override fun configure(http: HttpSecurity) {
        http {
            csrf {
                disable() // 取消表单请求跨域保护
            }
            authorizeRequests {
                authorize("/**", permitAll) // 先放行所有
            }
            addFilterAt(jwtTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            exceptionHandling {
                authenticationEntryPoint = UnauthorizedEntryPoint()
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

// 登陆失败的处理
class UnauthorizedEntryPoint : AuthenticationEntryPoint {
    override fun commence(request: HttpServletRequest?,
                          response: HttpServletResponse?,
                          e: AuthenticationException?) {
        val mapper = ObjectMapper()
        val map = mutableMapOf<String, Any>().apply {
            this["code"] = AuthError.LOGIN_ERROR.code
            this["msg"] = AuthError.LOGIN_ERROR.msg
        }
        val json = mapper.writeValueAsString(map)
        response?.characterEncoding = "utf-8"
        response?.contentType = "application/json;charset=utf-8"
        response?.writer?.write(json)
    }
}

/**
 * JWT 过滤器
 */
@Component
class JwtAuthenticationTokenFilter(private var config: JwtConfig)
    : OncePerRequestFilter() {
    companion object : Logging


    override fun doFilterInternal(request: HttpServletRequest, response:
    HttpServletResponse, filterChain: FilterChain) {
        // 拿到 authorization 对应的信息。事实上是可空的。
        val authToken: String? = request.getHeader(config.accessToken)
        val refreshToken: String? = request.getHeader(config.refreshToken)
        // 必须有相关信息
        if (authToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
            filterChain.doFilter(request, response)
            return
        }
        // 验证 refreshToken 没有过期
        else if (JwtTokenUtil.validateToken(refreshToken)) {
            val requestURL = request.requestURL
            // 指定接口才能访问
            if (requestURL.endsWith("/account/refresh")) {
                saveUserInfoByToken(refreshToken)
            }
            filterChain.doFilter(request, response)
        }

    }

    private fun saveUserInfoByToken(token: String) {
        // 取出账号信息（id）
        val userId = JwtTokenUtil.extractUserId(token)
        // TODO complete me
//        val userDetails = userCache.fetchLoginInfo(userId)
//        if (userDetails != null) {
//            val authentication = UsernamePasswordAuthenticationToken(
//                userDetails,
//                userDetails.password,
//                userDetails.authorities)
//            // 将authentication信息放入到上下文对象中
//            SecurityContextHolder.getContext().authentication = authentication
    }

}
