package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysDictTypes
import com.llh.basicadmin.model.SysDictType
import com.llh.basicadmin.pojo.SimpleQuery
import com.llh.basicadmin.pojo.vo.SysDictTypeVO
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysDictTypeService
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.like
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.ktorm.expression.BinaryExpression
import org.springframework.stereotype.Service

@Service
class SysDictTypeServiceImpl : SysDictTypeService {
    override fun baseDB(): EntitySequence<SysDictType, SysDictTypes> {
        return DB.sequenceOf(SysDictTypes)
    }

    override fun pageAndQuery(pageVO: SimpleQuery<SysDictTypeVO>) {
        val condition = pageVO.condition
        if (condition == null) return
        val list = baseDB().filter {
            queryConditionCreate(it, condition)
        }.toList()
        println(list)
    }

    /**
     * 构建查询条件
     */
    private fun queryConditionCreate(table: SysDictTypes, condition: SysDictTypeVO)
        : BinaryExpression<Boolean> {
        var query = table.removeFlag eq false
        if (!condition.remark.isNullOrBlank()) {
            query = query and (table.remark like condition.remark!!)
        }
        if (!condition.displayName.isNullOrBlank()) {
            query = query and (table.displayName like condition.displayName!!)
        }
        return query
    }
}
