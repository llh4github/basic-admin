package com.llh.basicadmin.service.sys

import com.llh.basicadmin.dao.SysAuthorities
import com.llh.basicadmin.model.SysAuthority
import com.llh.basicadmin.service.BasicService

/**
 * SysAuthorityService
 *
 * CreatedAt: 2020-11-03 20:57
 *
 * @author llh
 */
interface SysAuthorityService :
    BasicService<SysAuthority, SysAuthorities> {
    /**
     * 获取指定角色id对应的权限列表
     */
    fun getListByRoleId(roleId: Int): List<SysAuthority>

    fun getListByRoleIds(roleIds: List<Int>): List<SysAuthority>
}
