package com.llh.basicadmin.daotest

import com.llh.basicadmin.dao.SysRoles
import com.llh.basicadmin.dao.SysUsers
import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.model.SysUser
import org.junit.jupiter.api.Test
import org.ktorm.database.Database
import org.ktorm.entity.add
import org.ktorm.entity.sequenceOf
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime

/**
 * SysUserDaoTest
 *
 * CreatedAt: 2020-10-27 21:38
 *
 * @author llh
 */
@SpringBootTest
class SysUserDaoTest {

    @Autowired
    private lateinit var database: Database

    @Test
    fun testAdd() {

        val sysUser = SysUser()
        sysUser.password = "aaa"
        sysUser.username = "Tom"
        sysUser.createdTime = LocalDateTime.now()
        database
            .sequenceOf(SysUsers)
            .add(sysUser)

    }

    @Test
    fun testAddRole() {
        val r = SysRole()
        r.displayName = "测试"
        r.roleName = "test"
        r.remark = "remark"
        r.createdTime = LocalDateTime.now()
        database
            .sequenceOf(SysRoles)
            .add(r)
    }
}