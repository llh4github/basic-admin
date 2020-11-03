package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysAuthorities
import com.llh.basicadmin.dao.SysRoles
import com.llh.basicadmin.model.SysAuthority
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.EffectOne
import com.llh.basicadmin.service.sys.SysAuthorityService
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.*
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SysAuthorityServiceImpl : SysAuthorityService {

    override fun baseDB(): EntitySequence<SysAuthority, SysAuthorities> {
        return DB.sequenceOf(SysAuthorities)
    }
}
