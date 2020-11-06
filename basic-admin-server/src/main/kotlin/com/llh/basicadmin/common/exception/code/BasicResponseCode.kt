package com.llh.basicadmin.common.exception.code

// 定义各种异常代码的起始编码。
// 在各子异常类中递加即可

internal const val business_error = 10000
internal const val auth_error = 40100
internal const val data_error = 80100


/**
 * BasicResponseCode  基本的响应代码
 *
 * CreatedAt: 2020-11-02 22:19
 *
 * @author llh
 */
enum class BasicResponseCode(val code: Int, val msg: String) {
    /**
     * 兜底错误。
     *
     * js中0为false
     */
    UNKNOWN(0, "未知错误"),
    SUCCESS(1, "OK"),
    AUTH_ERROR(auth_error, "授权异常"),
    DATA_ERROR(data_error, "数据异常"),
    BUSINESS_ERROR(business_error, "系统异常"),
}

enum class DataError(val code: Int, val msg: String) {
    VALIDATE_ERROR(data_error + 1, "数据验证失败"),
}
