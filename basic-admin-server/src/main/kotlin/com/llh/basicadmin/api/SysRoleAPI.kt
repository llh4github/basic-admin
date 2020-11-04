package com.llh.basicadmin.api

import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.pojo.RespWrapper
import com.llh.basicadmin.pojo.okResponse
import com.llh.basicadmin.pojo.vo.RoleVO
import com.llh.basicadmin.service.sys.SysRoleService
import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.common.validation.UpdateOperate
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 *
 * Created At 2020/10/28 15:27
 *
 * @author llh
 */
@RestController
@RequestMapping("sys/role")
@Api(tags = ["操作角色信息的API"])
class SysRoleAPI {
    @Autowired
    private lateinit var sysRoleService: SysRoleService

    @GetMapping("{id}")
    @ApiOperation("根据id获取角色信息")
    fun getById(@PathVariable id: Int): RespWrapper {
        val model = sysRoleService.findById(id)
        return okResponse(model)
    }

    @PostMapping(value = ["add"])
    @ApiOperation("添加角色")
    fun add(@RequestBody @Validated(AddOperate::class)
            roleVO: RoleVO): RespWrapper {
        val model = SysRole {
            displayName = roleVO.displayName!!
            roleName = roleVO.roleName!!
        }
        return okResponse(sysRoleService
            .saveWithAuthorities(model, roleVO.authorities))
    }

    @PutMapping(value = ["update"])
    @ApiOperation("修改角色信息")
    fun update(@RequestBody @Validated(UpdateOperate::class)
               roleVO: RoleVO): RespWrapper {
        val model = SysRole {
            id = roleVO.id!!
            if (roleVO.displayName != null)
                displayName = roleVO.displayName
            if (roleVO.roleName != null)
                roleName = roleVO.roleName
        }
        return okResponse(sysRoleService
            .updateByIdWithAuthorities(model, roleVO.authorities))
    }
}
