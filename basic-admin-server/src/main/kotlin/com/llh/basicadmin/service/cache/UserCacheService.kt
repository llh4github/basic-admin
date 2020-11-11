package com.llh.basicadmin.service.cache

import com.llh.basicadmin.common.exception.AppException
import com.llh.basicadmin.common.exception.code.AuthError
import com.llh.basicadmin.common.util.JacksonUtils
import com.llh.basicadmin.dao.RedisDao
import com.llh.basicadmin.pojo.AccountInfo
import com.llh.basicadmin.pojo.AuthTokenVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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

    fun loginKey(userId: Int): String {
        return "login-info:$userId"
    }


    fun cacheLoginInfo(tokenVo: AuthTokenVO, vo: AccountInfo) {
        vo.id ?: throw AppException(AuthError.LOGIN_ERROR)

        val hashMap = mapOf(
            "access" to tokenVo.access,
            "refresh" to tokenVo.refresh,
            "userInfo" to JacksonUtils.writeValueAsString(vo),
        )
        redisDao.hmset(loginKey(vo.id), hashMap)

    }
}
