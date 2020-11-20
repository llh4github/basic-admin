package com.llh.basicadmin.dao

import com.llh.basicadmin.model.*
import org.ktorm.schema.boolean
import org.ktorm.schema.int
import org.ktorm.schema.varchar

/**
 * SysModelDao 系统模块表的dao层映射
 *
 * CreatedAt: 2020-10-27 21:37
 *
 * @author llh
 */
object SysUsers : BasicDao<SysUser>("sys_user") {
    val username = varchar("username").bindTo { it.username }
    val password = varchar("password").bindTo { it.password }
}

object SysRoles : BasicDao<SysRole>("sys_role") {
    val roleName = varchar("role_name").bindTo { it.roleName }
    val displayName = varchar("display_name").bindTo { it.displayName }
    val remark = varchar("remark").bindTo { it.remark }
}

object SysAuthorities : BasicDao<SysAuthority>("sys_authority") {
    val name = varchar("name").bindTo { it.name }
    val remark = varchar("remark").bindTo { it.remark }
}

object SysDictTypes : BasicDao<SysDictType>("sys_dict_type") {
    val name = varchar("name").bindTo { it.name }
    val displayName = varchar("display_name").bindTo { it.displayName }
    val remark = varchar("remark").bindTo { it.remark }
}

object SysDictDates : BasicDao<SysDictData>("sys_dict_data") {
    val name = varchar("name").bindTo { it.name }
    val displayName = varchar("display_name").bindTo { it.displayName }
    val remark = varchar("remark").bindTo { it.remark }
    val typeName = varchar("type_name").bindTo { it.typeName }
    val defaultFlag = boolean("default_flag").bindTo { it.defaultFlag }
    val sortNo = int("sort_no").bindTo { it.sortNo }
}

object SysDepts : BasicDao<SysDept>("sys_dept") {
    val name = varchar("name").bindTo { it.name }
    val remark = varchar("remark").bindTo { it.remark }
    val sortNo = int("sort_no").bindTo { it.sortNo }
    val parentId = int("parent_id").bindTo { it.parentId }
}
