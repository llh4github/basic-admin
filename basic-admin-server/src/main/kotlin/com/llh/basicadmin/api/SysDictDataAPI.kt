package com.llh.basicadmin.api

import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.model.SysDictData
import com.llh.basicadmin.pojo.RespWrapper
import com.llh.basicadmin.pojo.okResponse
import com.llh.basicadmin.pojo.vo.SysDictDataVO
import com.llh.basicadmin.service.sys.SysDictDataService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

/**
 *
 * Created At 2020/11/9 10:18
 *
 * @author llh
 */
@RestController
@RequestMapping("sys/dict/data")
@Api(tags = ["操作字典数据信息的API"])
class SysDictDataAPI {
    @Autowired
    private lateinit var sysDictDataService: SysDictDataService

    @GetMapping("{id}")
    @ApiOperation("根据id获取字典数据信息")
    fun getById(@PathVariable id: Int): RespWrapper {
        val model = sysDictDataService.findById(id)
        return okResponse(model)
    }

    @PostMapping(value = ["add"])
    @ApiOperation("添加字典数据")
    fun add(@RequestBody @Validated(AddOperate::class)
            dataVO: SysDictDataVO): RespWrapper {
        val model = SysDictData {
            name = dataVO.name
            displayName = dataVO.displayName
            typeName = dataVO.typeName
            sortNo = dataVO.sortNo
            remark = dataVO.remark
        }
        val operate = sysDictDataService.save(model)
        return okResponse(operate)
    }

    @GetMapping("type/{typeName}")
    @ApiOperation("根据类型名获取其类型下的字典数据")
    fun getListByTypeName(
        @PathVariable typeName: String): RespWrapper {
        val list: List<SysDictData> = sysDictDataService.getListByTypeName(typeName)
        return okResponse(list)
    }

}
