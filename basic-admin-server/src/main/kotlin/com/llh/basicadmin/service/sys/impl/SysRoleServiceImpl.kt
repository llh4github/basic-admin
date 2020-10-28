package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysRoles
import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.model.copyProperties
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.sys.SysRoleService
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SysRoleServiceImpl : SysRoleService {

    override fun save(entity: SysRole): SysRole {
        entity.createdTime = LocalDateTime.now()
        DB.sequenceOf(SysRoles)
            .add(entity)

        return entity
    }

    override fun remove(id: Int): Boolean {
        val model = DB.sequenceOf(SysRoles)
            .find { it.id eq id }
        model?.removeFlag = true
        return model?.flushChanges() == 1
    }

    override fun updateById(entity: SysRole): Boolean {
        val model = findById(entity.id)
        model?.copyProperties(entity)
        model?.updatedTime = LocalDateTime.now()
        val flushChanges = model?.flushChanges()
        return flushChanges == 1
    }

    override fun findById(id: Int): SysRole? {
        val model = DB.sequenceOf(SysRoles)
            .find { it.id eq id and (it.removeFlag eq true) }
        return if (model == null) {
            logger.debug("not found ")
            null
        } else {
            model
        }
    }

}