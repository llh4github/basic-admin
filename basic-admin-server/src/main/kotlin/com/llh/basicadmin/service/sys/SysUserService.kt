package com.llh.basicadmin.service.sys

import com.llh.basicadmin.dao.SysUsers
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.service.BasicService

/**
 * SysUserService
 *
 * CreatedAt: 2020-11-04 22:08
 *
 * @author llh
 */
interface SysUserService : BasicService<SysUser, SysUsers> {
    /**
     * 保存用户信息及其角色信息
     */
    fun saveWithRoles(model: SysUser, roleIds: Set<Int>?): Boolean
}
