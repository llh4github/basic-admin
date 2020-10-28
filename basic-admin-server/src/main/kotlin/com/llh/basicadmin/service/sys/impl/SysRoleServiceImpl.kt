package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.dao.SysRoles
import com.llh.basicadmin.model.SysRole
import com.llh.basicadmin.service.DB
import com.llh.basicadmin.service.EffectOne
import com.llh.basicadmin.service.sys.SysRoleService
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.entity.add
import org.ktorm.entity.find
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.update
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SysRoleServiceImpl : SysRoleService {

    override fun save(entity: SysRole): Boolean {
        entity.createdTime = LocalDateTime.now()
        return DB.sequenceOf(SysRoles)
            .add(entity) == EffectOne
    }

    override fun removeById(entity: SysRole): Boolean {
        val model = SysRole()
        model.id = entity.id
        model.updatedTime = LocalDateTime.now()
        model.removeFlag = true
        model.updatedBy = entity.updatedBy
        return updateById(model)
    }

    override fun updateById(entity: SysRole): Boolean {
        entity.updatedTime = LocalDateTime.now()
        val updated = DB
            .sequenceOf(SysRoles)
            .update(entity)
        return updated == EffectOne
    }

    override fun findById(id: Int): SysRole? {
        val model = DB.sequenceOf(SysRoles)
            .find { it.id eq id and (it.removeFlag eq false) }
        return if (model == null) {
            logger.debug("not found role by id: $id")
            null
        } else {
            model
        }
    }

}
