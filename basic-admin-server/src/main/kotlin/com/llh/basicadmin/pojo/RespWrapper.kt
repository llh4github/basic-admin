package com.llh.basicadmin.pojo

import com.llh.basicadmin.common.exception.code.BasicResponseCode
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

/**
 * 响应结果包装类
 * Created At 2020/10/28 15:31
 *
 * @author llh
 */
@ApiModel("响应结果包装类")
data class RespWrapper(
    @ApiModelProperty(value = "响应代码")
    val code: Int,
    @ApiModelProperty(value = "提示信息")
    val msg: String?,
    @ApiModelProperty(value = "数据域")
    val data: Any? = null,
)

/**
 * 正确响应返回数据的函数
 */
fun okResponse(data: Any? = null,
               msg: String? = BasicResponseCode.SUCCESS.msg): RespWrapper {
    return RespWrapper(BasicResponseCode.SUCCESS.code, msg, data)
}
