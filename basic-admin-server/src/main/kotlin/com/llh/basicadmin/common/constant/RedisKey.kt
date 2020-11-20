package com.llh.basicadmin.common.constant

/**
 *
 * Created At 2020/11/12 13:32
 *
 * @author llh
 */
interface RedisKey {
}

/**
 * 帐户信息缓存键名
 */
object AccountCacheKey {

    val loginHMKey_access = "access"
    val loginHMKey_refresh = "refresh"

    fun loginInfoKey(userId: String) = "login-info:$userId"
}
