package com.llh.basicadmin.common.util

import com.llh.basicadmin.pojo.AccountInfo
import org.springframework.security.core.context.SecurityContextHolder

/**
 *
 * Created At 2020/11/20 13:37
 *
 * @author llh
 */
object UserUtils {

    /**
     * 获取当前用户信息
     *
     * 用户信息设置参考在 [com.llh.basicadmin.common.config.JwtAuthenticationTokenFilter]
     * 中的saveUserInfoInSecurityContext方法
     */
    fun currentUser(): AccountInfo {
        return SecurityContextHolder.getContext().authentication.details as AccountInfo
    }

    /**
     * 获取当前用户的id
     */
    fun currentUserId(): Int {
        return currentUser().id!! // 必定会有值的
    }
}
