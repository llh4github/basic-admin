package com.llh.basicadmin.model

import org.ktorm.entity.Entity
import java.time.LocalDateTime

/**
 * BasicModel 基础实体类
 *
 * CreatedAt: 2020-10-27 21:06
 * 写法源自  [这里](https://github.com/vincentlauvlwj/Ktorm/issues/113#issuecomment-598785235)
 * @author llh
 */
interface BasicModel<E : BasicModel<E>> : Entity<E> {
    var id: Int
    var createdTime: LocalDateTime
    var updatedTime: LocalDateTime
    var removeFlag: Boolean
    var updatedBy: Int?
    var createdBy: Int?
}