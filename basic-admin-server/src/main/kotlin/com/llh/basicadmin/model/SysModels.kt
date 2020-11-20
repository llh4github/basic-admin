/**
 * SysModels
 *
 * CreatedAt: 2020-10-27 21:17
 *
 * @author llh
 */
package com.llh.basicadmin.model

import com.llh.basicadmin.common.constant.ROLE_PREFIX
import org.ktorm.entity.Entity
import org.springframework.security.core.GrantedAuthority

/**
 * 系统用户实体类
 */
interface SysUser : BasicModel<SysUser> {
    companion object : Entity.Factory<SysUser>()

    var username: String
    var password: String
    var status: Int
}

interface SysRole : BasicModel<SysRole>, GrantedAuthority {
    companion object : Entity.Factory<SysRole>()

    var roleName: String

    var displayName: String
    var remark: String?

    /** 获取的字符串必须以 ROLE_ 开头 */
    override fun getAuthority(): String {
        return if (this.roleName.startsWith(ROLE_PREFIX)) this.roleName
        else ROLE_PREFIX + this.roleName
    }
}

interface SysAuthority : BasicModel<SysAuthority>, GrantedAuthority {
    companion object : Entity.Factory<SysAuthority>()

    var name: String
    var remark: String?

    override fun getAuthority(): String {
        return this.name
    }
}

interface SysDictType : BasicModel<SysDictType> {
    companion object : Entity.Factory<SysDictType>()

    var name: String
    var displayName: String
    var remark: String?
}

interface SysDictData : BasicModel<SysDictData> {
    companion object : Entity.Factory<SysDictData>()

    var name: String
    var displayName: String
    var remark: String?
    var sortNo: Int

    /** 值为[SysDictType.name] */
    var typeName: String
    var defaultFlag: Boolean
}

interface SysDept : BasicModel<SysDept> {
    companion object : Entity.Factory<SysDept>()

    var name: String
    var remark: String?
    var sortNo: Int
    var parentId: Int
}
