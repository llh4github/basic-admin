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
 * Created At 2020/11/4 10:16
 *
 * @author llh
 */
@ApiModel("权限元信息")
data class AuthorityVO(
    @field:NotNull(message = "id不能为空", groups = [UpdateOperate::class])
    @field:Min(value = 0, message = "id值必须为正整数", groups = [UpdateOperate::class])
    val id: Int?,

    @field:NotBlank(message = "权限元信息不能为空", groups = [AddOperate::class])
    @field:Pattern(regexp = "^[A-Za-z0-9_]+\$",
        message = "权限元信息只能由英文字母、数字和下划线组成",
        groups = [AddOperate::class, UpdateOperate::class])
    @ApiModelProperty(value = "权限元信息", notes = "权限元信息只能由英文字母、数字和下划线组成")
    val name: String?,

    @field:Length(max = 60, message = "备注信息在60个字符之内",
        groups = [AddOperate::class, UpdateOperate::class])
    val remark: String?,
)
