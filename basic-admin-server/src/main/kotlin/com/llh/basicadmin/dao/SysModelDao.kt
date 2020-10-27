package com.llh.basicadmin.dao

import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.model.SysUser
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