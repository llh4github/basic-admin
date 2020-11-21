package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysDepts
import com.llh.basicadmin.model.SysDept
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysDeptService
import org.ktorm.entity.EntitySequence
import org.ktorm.entity.sequenceOf
import org.springframework.stereotype.Service

@Service
class SysDeptServiceImpl : SysDeptService {
    override fun baseDB(): EntitySequence<SysDept, SysDepts> {
        return DB.sequenceOf(SysDepts)
    }
}
