package com.llh.basicadmin.service.cache

import com.llh.basicadmin.common.constant.AccountCacheKey
import com.llh.basicadmin.common.exception.AppException
import com.llh.basicadmin.common.exception.code.AuthError
import com.llh.basicadmin.dao.RedisDao
import com.llh.basicadmin.pojo.AccountInfo
import com.llh.basicadmin.pojo.AuthTokenVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

private typealias Key = AccountCacheKey

/**
 *
 * Created At 2020/11/11 14:46
 *
 * @author llh
 */
@Service
class UserCacheService {
    @Autowired
    private lateinit var redisDao: RedisDao


    fun cacheLoginInfo(tokenVo: AuthTokenVO, vo: AccountInfo) {
        vo.id ?: throw AppException(AuthError.LOGIN_ERROR)

        val hashMap = mapOf(
            Key.loginHMKey_access to tokenVo.access,
            Key.loginHMKey_refresh to tokenVo.refresh,
        )
        redisDao.hmset(Key.loginInfoKey("${vo.id}"), hashMap)
    }

    fun cleanLoginInfo(userId: Int) {
        redisDao.del(Key.loginInfoKey("$userId"))
    }

    fun getLoginAccessToken(userId: String): String? {
        return getLoginInfo(userId, Key.loginHMKey_access)
    }

    fun getLoginRefreshToken(userId: String): String? {
        return getLoginInfo(userId, Key.loginHMKey_refresh)
    }

    /**
     * 获取缓存中的用户信息。
     *
     * 返回的是map
     */
    private fun getLoginInfo(userId: String, hmKey: String): String? {
        val map = redisDao.hmget(Key.loginInfoKey(userId))
        return map[hmKey] as String?
    }
}
