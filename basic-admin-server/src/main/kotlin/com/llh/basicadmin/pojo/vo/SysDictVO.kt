package com.llh.basicadmin.pojo.vo

import com.llh.basicadmin.common.validation.AddAndUpdate
import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.common.validation.UpdateOperate
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 *
 * Created At 2020/11/9 10:25
 *
 * @author llh
 */
data class SysDictDataVO(
    @field:NotNull(message = "id不能为空", groups = [UpdateOperate::class])
    @field:Min(value = 0, message = "id值必须为正整数", groups = [UpdateOperate::class])
    val id: Int?,
    @field:Length(max = 30, message = "不能超过30位")
    val name: String,
    @field:Length(max = 30, message = "不能超过30位")
    val displayName: String,
    @field:Length(max = 150, message = "不能超过150位")
    val remark: String?,
    @field:Min(value = 0, message = "排序号必须为正整数")
    val sortNo: Int,

    @field:NotBlank(message = "字典类型名不能为空", groups = [AddOperate::class])
    val typeName: String,
    val defaultFlag: Boolean,
)

data class SysDictTypeVO(
    @field:NotNull(message = "id不能为空", groups = [UpdateOperate::class])
    @field:Min(value = 0, message = "id值必须为正整数", groups = [UpdateOperate::class])
    val id: Int?,

    @field:Length(max = 30, message = "不能超过30位",
        groups = [AddAndUpdate::class, UpdateOperate::class])
    @field:NotBlank(message = "不能为空", groups = [AddOperate::class])
    var name: String?,

    @get:Length(max = 30, message = "不能超过30位",
        groups = [AddOperate::class, UpdateOperate::class])
    @get:NotBlank(message = "不能为空", groups = [AddOperate::class])
    var displayName: String?,

    @field:Length(max = 150, message = "不能超过150位",
        groups = [AddAndUpdate::class, UpdateOperate::class])
    var remark: String?,
)
