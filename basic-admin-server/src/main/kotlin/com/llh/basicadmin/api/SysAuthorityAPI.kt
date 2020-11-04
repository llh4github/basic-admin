package com.llh.basicadmin.api

import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.common.validation.UpdateOperate
import com.llh.basicadmin.model.SysAuthority
import com.llh.basicadmin.pojo.RespWrapper
import com.llh.basicadmin.pojo.okResponse
import com.llh.basicadmin.pojo.vo.AuthorityVO
import com.llh.basicadmin.service.sys.SysAuthorityService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 *
 * Created At 2020/11/4 10:23
 *
 * @author llh
 */
@RestController
@RequestMapping("sys/authority")
@Api(tags = ["权限元信息的API"])
class SysAuthorityAPI {
    @Autowired
    private lateinit var sysAuthorityService: SysAuthorityService

    @GetMapping("role/{roleId}")
    @ApiOperation("获取指定角色拥有的权限列表")
    fun getListByRoleId(
        @PathVariable roleId: Int): RespWrapper {
        val list: List<SysAuthority> = sysAuthorityService.getListByRoleId(roleId)
        return okResponse(list)
    }

    @GetMapping("{id}")
    @ApiOperation("根据id获取权限元信息")
    fun getById(@PathVariable id: Int): RespWrapper {
        val model = sysAuthorityService.findById(id)
        return okResponse(model)
    }

    @PostMapping(value = ["add"])
    @ApiOperation("添加权限元信息")
    fun add(@RequestBody @Validated(AddOperate::class)
            authorityVO: AuthorityVO): RespWrapper {
        val model = SysAuthority {
            name = authorityVO.name!!
            remark = authorityVO.remark
        }
        return okResponse(sysAuthorityService.save(model))
    }

    @PutMapping(value = ["update"])
    @ApiOperation("修改权限元信息")
    fun update(@RequestBody @Validated(UpdateOperate::class)
               authorityVO: AuthorityVO): RespWrapper {
        val model = SysAuthority {
            id = authorityVO.id!!
            if (!authorityVO.name.isNullOrBlank())
                name = authorityVO.name
            if (!authorityVO.remark.isNullOrBlank())
                remark = authorityVO.remark
        }
        return okResponse(sysAuthorityService.updateById(model))
    }


}
