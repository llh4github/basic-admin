package com.llh.basicadmin.api

import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.model.SysDictType
import com.llh.basicadmin.pojo.RespWrapper
import com.llh.basicadmin.pojo.okResponse
import com.llh.basicadmin.pojo.vo.SysDictDataVO
import com.llh.basicadmin.pojo.vo.SysDictTypeVO
import com.llh.basicadmin.service.sys.SysDictTypeService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 *
 * Created At 2020/11/9 13:45
 *
 * @author llh
 */
@RestController
@RequestMapping("sys/dict/type")
@Api(tags = ["操作字典类型信息的API"])
class SysDictTypeAPI {
    @Autowired
    private lateinit var sysDictTypeService: SysDictTypeService

    @GetMapping("{id}")
    @ApiOperation("根据id获取字典类型信息")
    fun getById(@PathVariable id: Int): RespWrapper {
        val model = sysDictTypeService.findById(id)
        return okResponse(model)
    }

    @PostMapping(value = ["add"])
    @ApiOperation("添加字典类型信息")
    fun add(@RequestBody @Validated(AddOperate::class)
            typeVO: SysDictTypeVO): RespWrapper {
        val model = SysDictType {
            name = typeVO.name
            remark = typeVO.remark
            displayName = typeVO.displayName
        }
        val operate = sysDictTypeService.save(model)
        return okResponse(operate)
    }

}
