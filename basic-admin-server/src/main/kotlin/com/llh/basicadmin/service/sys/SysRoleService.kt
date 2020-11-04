package com.llh.basicadmin.service.sys

import com.llh.basicadmin.dao.SysRoles
import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.service.BasicService

/**
 *
 * Created At 2020/10/28 10:33
 *
 * @author llh
 */
interface SysRoleService : BasicService<SysRole, SysRoles> {
    /**
     * 保存角色信息及其权限信息
     */
    fun saveWithAuthorities(model: SysRole, authorities: Set<Int>?): Boolean

    /**
     * 更新角色信息及其权限信息
     */
    fun updateByIdWithAuthorities(model: SysRole, authorities: Set<Int>?): Boolean
}
