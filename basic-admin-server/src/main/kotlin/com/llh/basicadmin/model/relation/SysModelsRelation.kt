package com.llh.basicadmin.model.relation

import org.ktorm.entity.Entity

/**
 *
 * Created At 2020/10/29 17:57
 *
 * @author llh
 */
interface SysRoleAuthority : Entity<SysRoleAuthority> {
    companion object : Entity.Factory<SysRoleAuthority>()

    var id: Int
    var roleId: Int
    var authorityId: Int
}

interface SysUserRole : Entity<SysUserRole> {
    companion object : Entity.Factory<SysUserRole>()

    var id: Int
    var roleId: Int
    var userId: Int
}

interface SysDeptUser : Entity<SysDeptUser> {
    var id: Int
    var deptId: Int
    var userId: Int
}
