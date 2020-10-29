package com.llh.basicadmin.dao.relation

import com.llh.basicadmin.model.relation.SysUserAuthority
import org.ktorm.schema.Table
import org.ktorm.schema.int

/**
 *
 * Created At 2020/10/29 18:05
 *
 * @author llh
 */
object SysUserAuthorities : Table<SysUserAuthority>("sys_user_authority") {
    val id = int("id").primaryKey().bindTo { it.id }
    val authorityId = int("authority_id").bindTo { it.authorityId }
    val userId = int("user_id").bindTo { it.userId }
}
