package com.llh.basicadmin.pojo.vo

import com.llh.basicadmin.validation.AddOperate
import com.llh.basicadmin.validation.UpdateOperate
import io.swagger.annotations.ApiModel
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

/**
 *
 * Created At 2020/10/28 16:57
 *
 * @author llh
 */
@ApiModel("角色信息")
data class RoleVO(
    @field:NotNull(message = "id不能为空", groups = [UpdateOperate::class])
    @field:Min(value = 0, message = "id值必须为正整数", groups = [UpdateOperate::class])
    val id: Int?,
    @field:NotBlank(message = "角色代号不能为空", groups = [AddOperate::class])
    val roleName: String?,
    @field:NotBlank(message = "角色名不能为空", groups = [AddOperate::class])
    val displayName: String?
)
