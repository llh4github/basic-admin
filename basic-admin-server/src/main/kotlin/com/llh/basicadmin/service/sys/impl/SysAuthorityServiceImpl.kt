package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysAuthorities
import com.llh.basicadmin.dao.relation.SysRoleAuthorities
import com.llh.basicadmin.model.SysAuthority
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysAuthorityService
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.springframework.stereotype.Service

@Service
class SysAuthorityServiceImpl : SysAuthorityService {

    override fun baseDB(): EntitySequence<SysAuthority, SysAuthorities> {
        return DB.sequenceOf(SysAuthorities)
    }

    override fun getListByRoleId(roleId: Int): List<SysAuthority> {
        val authorityIds = DB.sequenceOf(SysRoleAuthorities)
            .filter { it.roleId eq roleId }
            .map { it.authorityId }
            .toList()

        if (authorityIds.isEmpty()) return emptyList()
        return baseDB()
            .filter { it.id.inList(authorityIds) }
            .toList()
    }

    override fun getListByRoleIds(roleIds: List<Int>): List<SysAuthority> {
        if (roleIds.isEmpty()) return emptyList()
        val authorityIds = DB.sequenceOf(SysRoleAuthorities)
            .filter { it.roleId.inList(roleIds) }
            .map { it.authorityId }
            .toList()

        if (authorityIds.isEmpty()) return emptyList()
        return baseDB()
            .filter { it.id.inList(authorityIds) }
            .toList()
    }
}
