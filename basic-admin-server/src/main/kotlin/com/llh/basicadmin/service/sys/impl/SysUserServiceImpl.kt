package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysUsers
import com.llh.basicadmin.dao.relation.SysUserRoles
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysUserService
import org.ktorm.dsl.batchInsert
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.sequenceOf
import org.springframework.stereotype.Service

@Service
class SysUserServiceImpl : SysUserService {
    override fun baseDB(): EntitySequence<SysUser, SysUsers> {
        return DB.sequenceOf(SysUsers)
    }

    override fun saveWithRoles(model: SysUser,
                               roleIds: Set<Int>?): Boolean {
        save(model)
        addUserRoles(model, roleIds)
        return true
    }


// ------ private ------

    /**
     * 添加 用户-角色 关系
     */
    private fun addUserRoles(model: SysUser, roleIds: Set<Int>?) {
        if (roleIds == null) return
        DB.batchInsert(SysUserRoles) {
            roleIds.forEach { ele ->
                item {
                    set(it.roleId, ele)
                    set(it.userId, model.id)
                }
            }
        }

    }
}
