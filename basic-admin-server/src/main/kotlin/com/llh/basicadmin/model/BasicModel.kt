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
/**
 * 默认不拷贝的属性列表。防止数据被破坏。
 * 只读集合。
 */
val NOT_COPY_PROPERTIES = setOf(
    "createdTime", "createdBy", "updatedBy", "password")
/**
 * 从另一个实体类中拷贝属性。
 * 通常用于更新数据库的部分字段。
 * [source] 是拷贝源。用它的值更新调用者对应的属性值。
 * [notCopy] 某些字段不被拷贝。默认不拷贝见 [NOT_COPY_PROPERTIES]
 * 这个方法还不太完善。先用用看吧。
 */
fun <E : BasicModel<E>> BasicModel<E>.copyProperties(
    source: E, notCopy: Set<String> = NOT_COPY_PROPERTIES) {
    if (this == source) return
    for ((k, v) in source.properties) {
        if (notCopy.contains(k)) continue
        if (null == v) continue
        if (k == "id") {
            if (this[k] != null) continue
        }
        this[k] = v
    }
}