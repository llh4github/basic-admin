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
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter(), WebMvcConfigurer {
    @Autowired
    private lateinit var accountService: AccountService

    @Autowired
    private lateinit var jwtTokenFilter: JwtAuthenticationTokenFilter
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
            .allowedMethods("*")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600L * 24) //maxAge(3600)表明在3600秒内，不需要再发送预检验请求，可以缓存该结果
    }

    override fun configure(http: HttpSecurity) {
        http {
            csrf {
                disable() // 取消表单请求跨域保护
            }
            cors {
                disable() //
            }
            sessionManagement {
                // 基于token，所以不需要session
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeRequests {
                authorize("/", permitAll)
                authorize("/*.html", permitAll)
                authorize("/**/*.js", permitAll)
                authorize("/**/*.html", permitAll)
                authorize("/**/*.css", permitAll)
                authorize("/swagger/**", permitAll)
                authorize("/actuator/**", permitAll)
                authorize("/v2/api-docs", permitAll)
                authorize("/swagger-ui.html", permitAll)
                authorize("/swagger-resources/**", permitAll)
                authorize("/druid/**", permitAll)
                authorize("/webjars/**", permitAll)
                authorize("/favicon.ico", permitAll)


                authorize("/account/**", permitAll)
                authorize()
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

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
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
        var err = AuthError.LOGIN_ERROR
        if (e is BadCredentialsException) {
            err = AuthError.NAME_PWD_ERROR
        }
        val map = mutableMapOf<String, Any>().apply {
            this["code"] = err.code
            this["msg"] = err.msg
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

    @Autowired
    private lateinit var accountService: AccountService

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {
        // 拿到 authorization 对应的信息。事实上是可空的。
        val authToken: String? = request.getHeader(config.accessToken)
        val refreshToken: String? = request.getHeader(config.refreshToken)

        // 必须有相关信息
        if (authToken.isNullOrEmpty() || refreshToken.isNullOrEmpty()) {
            filterChain.doFilter(request, response)
            return
        }
        // 验证 authToken 没有过期
        if (JwtTokenUtil.validateToken(authToken)) {
            val username = JwtTokenUtil.extractUsername(authToken)
            if (!username.isNullOrEmpty()) {
                saveUserInfoInSecurityContext(username)
                filterChain.doFilter(request, response)
                return
            }
        }
        // 验证 refreshToken 没有过期
        if (JwtTokenUtil.validateToken(refreshToken)) {
            val requestURL = request.requestURL
            // 指定接口才能访问
            if (requestURL.endsWith("/account/refresh")) {
                val username = JwtTokenUtil.extractUsername(refreshToken)
                if (!username.isNullOrEmpty()) {
                    saveUserInfoInSecurityContext(refreshToken)
                }
            }
            filterChain.doFilter(request, response)
        }

    }

    /**
     * 保存用户信息到SpringSecurity上下文中
     */
    private fun saveUserInfoInSecurityContext(username: String) {
        val loginUserInfo = accountService.loadUserByUsername(username)
        val authenticationToken = UsernamePasswordAuthenticationToken(
            loginUserInfo?.username,
            loginUserInfo?.password,
            loginUserInfo?.authorities)
        // 用户信息放在 details 字段里，可以在程序其他地方获取用户信息
        authenticationToken.details = loginUserInfo
        SecurityContextHolder
            .getContext()
            .authentication = authenticationToken
    }
}
