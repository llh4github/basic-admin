package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysDictTypes
import com.llh.basicadmin.model.SysDictType
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysDictTypeService
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.sequenceOf
import org.springframework.stereotype.Service

@Service
class SysDictTypeServiceImpl : SysDictTypeService {
    override fun baseDB(): EntitySequence<SysDictType, SysDictTypes> {
        return DB.sequenceOf(SysDictTypes)
    }
}
