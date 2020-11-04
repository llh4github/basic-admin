package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysRoles
import com.llh.basicadmin.dao.relation.SysRoleAuthorities
import com.llh.basicadmin.dao.relation.SysUserRoles
import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysRoleService
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.springframework.stereotype.Service

@Service
class SysRoleServiceImpl : SysRoleService {


    override fun baseDB(): EntitySequence<SysRole, SysRoles> {
        return DB.sequenceOf(SysRoles)
    }

    override fun saveWithAuthorities(
        model: SysRole, authorities: Set<Int>?): Boolean {
        save(model)
        addRoleAuthorities(model, authorities)

        return true
    }

    override fun updateByIdWithAuthorities(
        model: SysRole, authorities: Set<Int>?): Boolean {
        updateById(model)
        DB.delete(SysRoleAuthorities) {
            it.roleId eq model.id
        }
        addRoleAuthorities(model, authorities)

        return true
    }

    override fun getListByUserId(userId: Int): List<SysRole> {
        val toList = DB.sequenceOf(SysUserRoles)
            .filter { it.userId eq userId }
            .map { it.roleId }
            .toList()
        if (toList.isEmpty()) return emptyList()

        return baseDB().filter { it.id.inList(toList) }
            .toList()
    }
// ------ private ------

    /**
     * 添加角色-权限关系
     */
    private fun addRoleAuthorities(model: SysRole, authorities: Set<Int>?) {
        if (authorities == null) return
        DB.batchInsert(SysRoleAuthorities) {
            authorities.forEach { ele ->
                item {
                    set(it.authorityId, ele)
                    set(it.roleId, model.id)
                }
            }
        }
    }
}
