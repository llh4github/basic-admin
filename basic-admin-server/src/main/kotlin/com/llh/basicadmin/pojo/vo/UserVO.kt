package com.llh.basicadmin.pojo.vo

import com.llh.basicadmin.common.validation.AddOperate
import com.llh.basicadmin.common.validation.UpdateOperate
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 *
 * Created At 2020/11/6 9:17
 *
 * @author llh
 */
@ApiModel("用户信息操作VO")
data class UserVO(
    @field:NotNull(message = "id不能为空", groups = [UpdateOperate::class])
    @field:Min(value = 0, message = "id值必须为正整数", groups = [UpdateOperate::class])
    val id: Int?,

    @field:NotNull(message = "用户名不能为空", groups = [AddOperate::class])
    val username: String?,

    @field:NotNull(message = "密码不能为空", groups = [AddOperate::class])
    @field:Length(min = 6, message = "密码不能低于6位")
    val password: String?,

    @ApiModelProperty(value = "用户对应角色信息的id值")
    val roles: Set<Int>?,
)

@ApiModel("用户信息显示VO")
data class UserInfoVO(
    val username: String,
    val roles: Set<String> = mutableSetOf(),
    val permissions: Set<String> = mutableSetOf(),
    val avatar: String = "https://s3.ax1x.com/2020/11/22/D8E5vR.jpg", // 先用个临时的
)
