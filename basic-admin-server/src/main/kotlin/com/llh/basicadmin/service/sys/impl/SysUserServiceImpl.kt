package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysUsers
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysUserService
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.sequenceOf
import org.springframework.stereotype.Service

@Service
class SysUserServiceImpl : SysUserService {
    override fun baseDB(): EntitySequence<SysUser, SysUsers> {
        return DB.sequenceOf(SysUsers)
    }
}
