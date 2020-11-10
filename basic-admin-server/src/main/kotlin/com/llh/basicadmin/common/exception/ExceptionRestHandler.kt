package com.llh.basicadmin.common.exception

import com.llh.basicadmin.common.exception.code.BasicResponseCode
import com.llh.basicadmin.common.exception.code.DataError
import com.llh.basicadmin.common.util.SpringUtils
import com.llh.basicadmin.pojo.RespWrapper
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.Exception

/**
 * 统一异常处理
 * Created At 2020/10/28 17:20
 *
 * @author llh
 */
@RestControllerAdvice
class ExceptionRestHandler {

    /**
     * 处理字段验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): RespWrapper {
        val msg = e
            .bindingResult
            .fieldErrors
            .joinToString(separator = ",")
            { "" + it.defaultMessage }
        return RespWrapper(DataError.VALIDATE_ERROR.code, msg, null)
    }

    @ExceptionHandler(AppException::class)
    fun handleAppException(e: AppException): RespWrapper {
        if (SpringUtils.isDevProfile()) {
            e.printStackTrace()
        }
        return RespWrapper(e.info.getExpCode(), e.info.getExpMsg(), null)
    }

    /**
     * 未知异常。
     * 兜底的。
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): RespWrapper {
        if (SpringUtils.isDevProfile()) {
            e.printStackTrace()
        }
        return RespWrapper(BasicResponseCode.UNKNOWN.code, BasicResponseCode.UNKNOWN.msg, null)
    }
}
