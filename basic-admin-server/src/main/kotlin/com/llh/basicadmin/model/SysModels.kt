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

interface SysAuthority : BasicModel<SysAuthority> {
    companion object : Entity.Factory<SysAuthority>()

    var name: String
    var remark: String?
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
