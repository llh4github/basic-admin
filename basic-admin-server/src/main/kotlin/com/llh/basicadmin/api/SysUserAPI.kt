package com.llh.basicadmin.api

import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.pojo.RespWrapper
import com.llh.basicadmin.pojo.okResponse
import com.llh.basicadmin.pojo.vo.UserVO
import com.llh.basicadmin.service.sys.SysUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * SysUserAPI
 *
 * CreatedAt: 2020-11-04 22:06
 *
 * @author llh
 */
@RestController
@RequestMapping("sys/user")
@Api(tags = ["用户信息的API"])
class SysUserAPI {
    @Autowired
    private lateinit var sysUserService: SysUserService

    @PostMapping(value = ["add"])
    @ApiOperation("添加用户信息")
    fun add(@RequestBody @Validated(AddOperate::class)
            userVO: UserVO): RespWrapper {
        val model = SysUser {
            username = userVO.username!!
            password = userVO.password!!
        }
        val saved: Boolean = sysUserService.saveWithRoles(model, userVO.roles)
        return okResponse(saved)
    }
}
