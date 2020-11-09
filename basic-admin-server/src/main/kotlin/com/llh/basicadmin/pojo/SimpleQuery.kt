package com.llh.basicadmin.pojo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.springframework.validation.annotation.Validated
import javax.validation.Valid

/**
 * 简单的查询参数包装类
 * Created At 2020/11/9 14:03
 *
 * @author llh
 */
@ApiModel("简单的查询参数包装类")
data class SimpleQuery<T>(
    @ApiModelProperty("当前页码")
    val pageNum: Int,
    @ApiModelProperty("分页大小")
    val pageSize: Int,
    @ApiModelProperty("简单的查询条件。查询逻辑是固定的")
    val condition: T?,
)
