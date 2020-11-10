package com.llh.basicadmin.service

import com.llh.basicadmin.model.BasicModel
import com.llh.basicadmin.common.util.SpringUtils
import com.llh.basicadmin.dao.BasicDao
import org.apache.logging.log4j.kotlin.Logging
import org.ktorm.database.Database
import org.ktorm.dsl.and
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.entity.*
import java.time.LocalDateTime
import kotlin.math.E

/**
 * 基本服务层。定义了基本的单表操作。
 * Created At 2020/10/28 9:47
 * 泛型使用[E][com.llh.basicadmin.model.BasicModel]
 * @author llh
 */
interface BasicService<E : BasicModel<E>, M : BasicDao<E>> : Logging {

    fun baseDB(): EntitySequence<E, M>

    /**
     * 保存
     */
    fun save(entity: E): Boolean {
        entity.createdTime = LocalDateTime.now()
        return baseDB().add(entity) == EffectOne
    }

    /**
     * 移除。entity.id属性不能为空
     *
     * 此方法仅更新 [BasicModel.updatedTime]、[BasicModel.updatedBy]和[BasicModel.removeFlag]字段
     */
    fun removeById(entity: E): Boolean {
        entity.properties.forEach { (k, _) ->
            if (k == "updatedTime")
                entity[k] = LocalDateTime.now()
            if (k == "removeFlag")
                entity[k] = true
            if (k == "updatedBy" || k == "id") {
                // do nothing
                // 字段值需要保留
            }
            // 其他字段值需要被置空
            entity[k] = null
        }
        return baseDB().update(entity) == EffectOne
    }

    /**
     * 根据id更新信息 entity.id属性不能为空
     *
     * 此方法仅更新非空字段
     */
    fun updateById(entity: E): Boolean {
        entity.updatedTime = LocalDateTime.now()
        return baseDB().update(entity) == EffectOne
    }

    /**
     * 根据id查找信息。可能找不到。
     */
    fun findById(id: Int): E? {
        return baseDB().find { it.id eq id and (it.removeFlag eq false) }
    }
}

/**
 * 数据库连接实例。
 *
 * 仅在service层中使用
 */
internal val DB = SpringUtils.getBean(Database::class.java)

/**
 * 影响一行数据。
 *
 * 用来判断单条数据的插入、更新、移除操作的正确性的。
 */
internal const val EffectOne = 1
