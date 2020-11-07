package com.llh.basicadmin.pojo

/**
 * PageHelper 分页结果辅助类
 * 理论上此类仅在函数[com.llh.basicadmin.dao.BasicDaoKt.findByPage]中使用
 * CreatedAt: 2020-11-07 15:27
 *
 * @author llh
 */
data class PageHelper<T>(
    val pageNum: Int,
    val pageSize: Int,
    val totalPage: Int,
    val data: List<T?>,
)
