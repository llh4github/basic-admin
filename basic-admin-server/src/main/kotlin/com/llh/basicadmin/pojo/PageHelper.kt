package com.llh.basicadmin.pojo

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * PageHelper 分页结果辅助类
 * 理论上此类仅在函数[com.llh.basicadmin.dao.BasicDaoKt.findByPage]中使用
 * CreatedAt: 2020-11-07 15:27
 *
 * @author llh
 */
@ApiModel("分页结果")
data class PageHelper<T>(
    @ApiModelProperty("当前页码")
    val pageNum: Int,
    @ApiModelProperty("分页大小")
    val pageSize: Int,
    @ApiModelProperty("总页数")
    val totalPage: Int,
    @ApiModelProperty("总数量")
    val totalRecord: Int,
    @ApiModelProperty("数据区")
    val data: List<T?>,
)
