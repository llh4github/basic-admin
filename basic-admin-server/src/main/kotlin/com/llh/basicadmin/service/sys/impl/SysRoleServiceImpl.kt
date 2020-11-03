package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysRoles
import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.EffectOne
import com.llh.basicadmin.service.sys.SysRoleService
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.update
import org.ktorm.entity.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SysRoleServiceImpl : SysRoleService {

    override fun baseDB(): EntitySequence<SysRole, SysRoles> {
        return DB.sequenceOf(SysRoles)
    }
}
