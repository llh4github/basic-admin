package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysDictDates
import com.llh.basicadmin.model.SysDictData
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysDictDataService
import org.ktorm.dsl.eq
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.filter
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList
import org.springframework.stereotype.Service

@Service
class SysDictDataServiceImpl : SysDictDataService {
    override fun baseDB(): EntitySequence<SysDictData, SysDictDates> {
        return DB.sequenceOf(SysDictDates)
    }

    override fun getListByTypeName(typeName: String): List<SysDictData> {
        return baseDB()
            .filter { it.typeName eq typeName }
            .filter { it.removeFlag eq false }
            .toList()
    }
}
