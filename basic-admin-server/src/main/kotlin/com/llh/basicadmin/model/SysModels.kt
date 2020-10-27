/**
 * SysModels
 *
 * CreatedAt: 2020-10-27 21:17
 *
 * @author llh
 */
package com.llh.basicadmin.model

import org.ktorm.entity.Entity

/**
 * 系统用户实体类
 */
interface SysUser : BasicModel<SysUser> {
    companion object : Entity.Factory<SysUser>()

    var username: String

    var password: String

    var status: Int
}

interface SysRole : BasicModel<SysRole> {
    companion object : Entity.Factory<SysRole>()

    var roleName: String

    var displayName: String

    var remark: String?

}