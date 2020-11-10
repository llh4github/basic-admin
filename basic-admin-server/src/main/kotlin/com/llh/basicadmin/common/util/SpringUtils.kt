package com.llh.basicadmin.common.util

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.core.env.Profiles
import org.springframework.stereotype.Component

/**
 * Spring相关的工具类
 * Created At 2020/10/28 10:40
 *
 * @author llh
 */
object SpringUtils {

    lateinit var applicationContext: ApplicationContext

    /**
     * 按类型获取bean
     */
    fun <T> getBean(c: Class<T>): T {
        return applicationContext.getBean(c)
    }

    /**
     * 当前是否使用的是dev配置
     */
    fun isDevProfile(): Boolean {
        return isProfiles("dev")
    }

    /**
     * 当前使用的是否是指定的配置文件
     */
    fun isProfiles(vararg profiles: String): Boolean {
        return applicationContext.environment.acceptsProfiles(Profiles.of(*profiles))
    }
}

/**
 * 初始化Spring工具类
 *
 * 此类是为了实现工具类的静态调用
 * 不得作他用
 */
@Component
class InitUtil : ApplicationContextAware {
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringUtils.applicationContext = applicationContext
    }
}
