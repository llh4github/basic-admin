package com.llh.basicadmin.dao

import com.llh.basicadmin.model.BasicModel
import org.ktorm.schema.*

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
