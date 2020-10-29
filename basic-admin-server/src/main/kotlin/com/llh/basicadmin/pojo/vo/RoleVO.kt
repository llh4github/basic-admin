package com.llh.basicadmin.pojo.vo

import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.common.validation.UpdateOperate
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

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
    @field:Pattern(regexp = "^[A-Za-z0-9_]+\$",
        message = "角色代号只能由英文字母、数字和下划线组成",
        groups = [AddOperate::class, UpdateOperate::class])
    @field:Length(max = 30, message = "角色代号在30个字符之内",
        groups = [AddOperate::class, UpdateOperate::class])
    @ApiModelProperty(value = "角色代号", notes = "角色代号只能由英文字母、数字和下划线组成")
    val roleName: String?,


    @field:NotBlank(message = "角色名不能为空", groups = [AddOperate::class])
    @field:Length(max = 30, message = "角色名在30个字符之内",
        groups = [AddOperate::class, UpdateOperate::class])
    val displayName: String?,
)
