package com.llh.basicadmin.service.cache

import com.llh.basicadmin.dao.RedisDao
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
class UserCache {
    @Autowired
    private lateinit var redisDao: RedisDao

    fun loginInfo(tokenVO: AuthTokenVO) {


    }
}
