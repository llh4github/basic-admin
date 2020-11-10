package com.llh.basicadmin.common.exception.code

// 定义各种异常代码的起始编码。
// 在各子异常类中递加即可

private const val business_error = 10000
private const val user_error = 15000
private const val auth_error = 40100
private const val data_error = 80100

interface ExpInfo {
    fun getExpCode(): Int
    fun getExpMsg(): String
}

/**
 * BasicResponseCode  基本的响应代码
 *
 * CreatedAt: 2020-11-02 22:19
 *
 * @author llh
 */
enum class BasicResponseCode(val code: Int, val msg: String) : ExpInfo {
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
    ;

    override fun getExpCode(): Int {
        return this.code
    }

    override fun getExpMsg(): String {
        return this.msg
    }
}

enum class DataError(val code: Int, val msg: String) : ExpInfo {
    VALIDATE_ERROR(data_error + 1, "数据验证失败"),
    ;

    override fun getExpCode(): Int {
        return this.code
    }

    override fun getExpMsg(): String {
        return this.msg
    }
}

enum class UserError(val code: Int, val msg: String) : ExpInfo {

    USERNAME_DUPLICATE(user_error + 1, "用户名已存在"),

    ;

    override fun getExpCode(): Int {
        return this.code
    }

    override fun getExpMsg(): String {
        return this.msg
    }
}
