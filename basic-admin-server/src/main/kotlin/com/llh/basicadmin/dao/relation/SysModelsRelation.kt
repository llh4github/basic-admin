package com.llh.basicadmin.dao.relation

import com.llh.basicadmin.model.relation.SysRoleAuthority
import com.llh.basicadmin.model.relation.SysUserRole
import org.ktorm.schema.Table
import org.ktorm.schema.int

/**
 *
 * Created At 2020/10/29 18:05
 *
 * @author llh
 */
object SysRoleAuthorities : Table<SysRoleAuthority>("sys_role_authority") {
    val id = int("id").primaryKey().bindTo { it.id }
    val authorityId = int("authority_id").bindTo { it.authorityId }
    val roleId = int("role_id").bindTo { it.roleId }
}

object SysUserRoles : Table<SysUserRole>("sys_user_role") {
    val id = int("id").primaryKey().bindTo { it.id }
    val userId = int("user_id").bindTo { it.userId }
    val roleId = int("role_id").bindTo { it.roleId }
}
