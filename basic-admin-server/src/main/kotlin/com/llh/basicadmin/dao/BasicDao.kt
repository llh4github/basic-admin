package com.llh.basicadmin.dao

import com.llh.basicadmin.model.BasicModel
import com.llh.basicadmin.pojo.PageHelper
import org.ktorm.entity.*
import org.ktorm.schema.*
import kotlin.math.ceil

/**
 * BasicDao
 *
 * CreatedAt: 2020-10-27 21:20
 *
 * @author llh
 */
abstract class BasicDao<E : BasicModel<E>>(tableName: String) : Table<E>(tableName) {
    val id = int("id").primaryKey().bindTo { it.id }
    val createdTime = datetime("created_time").bindTo { it.createdTime }
    val updatedTime = datetime("updated_time").bindTo { it.updatedTime }

    val removeFlag = boolean("remove_flag").bindTo { it.removeFlag }
    val updatedBy = int("updated_by").bindTo { it.updatedBy }
    val createdBy = int("created_by").bindTo { it.createdBy }
}

/**
 * 分页拓展函数。
 *
 * 仅适用于实体类API，
 * 此函数会返回[PageHelper]类，所以尽可能在最后使用。
 *
 * @param predicate 此参数虽然是调用此函数时传入，
 *      但会把所有设定的条件都会反映在生成的SQL中。
 */
fun <E : Any, T : Table<E>> EntitySequence<E, T>.findByPage(
    pageNum: Int,
    pageSize: Int,
    predicate: (T) -> ColumnDeclaring<Boolean>
): PageHelper<E?> {
    val offset = pageSize * (pageNum - 1)
    val count = this.filter(predicate).count()
    val total = ceil(count / pageSize.toDouble()).toInt()
    val data = this.filter(predicate).drop(offset).take(pageSize).toList()
    return PageHelper(pageNum, pageSize, total, count, data)
}
