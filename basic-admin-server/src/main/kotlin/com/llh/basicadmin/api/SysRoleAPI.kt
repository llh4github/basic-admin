package com.llh.basicadmin.api

import com.llh.basicadmin.pojo.RespWrapper
import com.llh.basicadmin.pojo.okResponse
import com.llh.basicadmin.service.sys.SysRoleService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
}
