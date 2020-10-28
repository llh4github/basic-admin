package com.llh.basicadmin.service

import com.llh.basicadmin.model.BasicModel
import com.llh.basicadmin.util.SpringUtils
import org.apache.logging.log4j.kotlin.Logging
import org.ktorm.database.Database

/**
 * 基本服务层。定义了基本的单表操作。
 * Created At 2020/10/28 9:47
 * 泛型使用[E][com.llh.basicadmin.model.BasicModel]
 * @author llh
 */
interface BasicService<E> : Logging {


    /**
     * 保存
     */
    fun save(entity: E): Boolean

    /**
     * 移除。entity.id属性不能为空
     *
     * 此方法仅更新 [BasicModel.updatedTime]、[BasicModel.updatedBy]和[BasicModel.removeFlag]字段
     */
    fun removeById(entity: E): Boolean

    /**
     * 根据id更新信息 entity.id属性不能为空
     *
     * 此方法仅更新非空字段
     */
    fun updateById(entity: E): Boolean

    /**
     * 根据id查找信息。可能找不到。
     */
    fun findById(id: Int): E?
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
