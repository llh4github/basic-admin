package com.llh.basicadmin.api

import com.llh.basicadmin.common.constant.ROLE_PREFIX
import com.llh.basicadmin.common.util.UserUtils
import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.common.validation.UpdateOperate
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.pojo.RespWrapper
import com.llh.basicadmin.pojo.okResponse
import com.llh.basicadmin.pojo.vo.UserInfoVO
import com.llh.basicadmin.pojo.vo.UserVO
import com.llh.basicadmin.service.sys.SysUserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

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
        val saved = sysUserService.saveWithRoles(model, userVO.roles)
        return okResponse(saved)
    }

    @PutMapping(value = ["update"])
    @ApiOperation("更新用户信息")
    fun update(@RequestBody @Validated(UpdateOperate::class)
               userVO: UserVO): RespWrapper {
        val model = SysUser {
            if (!userVO.username.isNullOrBlank())
                username = userVO.username
            if (!userVO.password.isNullOrBlank())
                password = userVO.password
        }
        val updated = sysUserService.saveWithRoles(model, userVO.roles)
        return okResponse(updated)
    }

    @GetMapping("info")
    @ApiOperation("获取当前用户信息")
    @CrossOrigin
    fun getInfo(): RespWrapper {
        val user = UserUtils.currentUser()

        val authorities = user.authorities
        val roles = authorities
            .filter { it.authority.startsWith(ROLE_PREFIX) }
            .map { it.authority }
            .toSet()
        val permissions = authorities
            .filter { !it.authority.startsWith(ROLE_PREFIX) }
            .map { it.authority }
            .toSet()
        val info = UserInfoVO(
            username = user.username,
            roles = roles,
            permissions = permissions
        )
        return okResponse(info)
    }
}
