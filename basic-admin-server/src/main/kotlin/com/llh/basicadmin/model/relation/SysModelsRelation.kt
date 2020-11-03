package com.llh.basicadmin.model.relation

import org.ktorm.entity.Entity

/**
 *
 * Created At 2020/10/29 17:57
 *
 * @author llh
 */
interface SysUserAuthority : Entity<SysUserAuthority> {
    companion object : Entity.Factory<SysUserAuthority>()

    var id: Int
    var roleId: Int
    var authorityId: Int
}
