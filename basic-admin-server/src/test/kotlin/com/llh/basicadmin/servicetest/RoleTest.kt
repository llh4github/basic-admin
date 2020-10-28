package com.llh.basicadmin.servicetest

import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.service.sys.SysRoleService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 *
 * Created At 2020/10/28 11:02
 *
 * @author llh
 */
@SpringBootTest
class RoleTest {

    @Autowired
    private lateinit var sysRoleService: SysRoleService

    @Test
    fun testAdd() {
        val r = SysRole()
        r.roleName = "test"
        r.displayName = "显示名"
        r.remark = "没有"
        sysRoleService.save(r)
    }

    @Test
    fun testUpdate() {
        val r = SysRole()
//        r.roleName = "test"
        r.displayName = "显2示1名"
//        r.remark = "没有2"
        r.id = 1
        println(sysRoleService.updateById(r))
    }

    @Test
    fun testRemove() {
        val model = SysRole()
        model.id = 2
        model.remark = "2134"
        model.updatedBy = 3
        sysRoleService.removeById(model)

    }

    @Test
    fun testFind() {

        sysRoleService.findById(2)
        sysRoleService.findById(-1)

    }
}
