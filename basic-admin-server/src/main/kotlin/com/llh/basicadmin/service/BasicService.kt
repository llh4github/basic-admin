package com.llh.basicadmin.service

import com.llh.basicadmin.util.SpringUtils
import org.ktorm.database.Database

/**
 * 基本服务层。定义了基本的单表操作。
 * Created At 2020/10/28 9:47
 * 泛型使用[E][com.llh.basicadmin.model.BasicModel]
 * @author llh
 */
interface BasicService<E> {


    /**
     * 保存
     */
    fun save(entity: E): E

    /**
     * 移除
     */
    fun remove(id: Int): Boolean

    /**
     * 根据id更新信息 entity.id属性不能为空
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

