package com.llh.basicadmin.common.exception

import com.llh.basicadmin.pojo.RespWrapper
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

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
        return RespWrapper(5001, msg, null)
    }
}
