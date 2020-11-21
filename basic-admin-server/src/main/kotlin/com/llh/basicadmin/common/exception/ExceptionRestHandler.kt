package com.llh.basicadmin.common.exception

import com.llh.basicadmin.common.exception.code.AuthError
import com.llh.basicadmin.common.exception.code.BasicResponseCode
import com.llh.basicadmin.common.exception.code.DataError
import com.llh.basicadmin.common.util.SpringUtils
import com.llh.basicadmin.pojo.RespWrapper
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
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
class ExceptionRestHandler : Logging {

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
        return RespWrapper(DataError.VALIDATE_ERROR.code, msg)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): RespWrapper {
        if (SpringUtils.isDevProfile()) {
            e.printStackTrace()
        }
        logger.debug("AccessDeniedException  :", e.fillInStackTrace())
        return RespWrapper(
            AuthError.ACCESS_DENIED.code,
            AuthError.ACCESS_DENIED.msg)
    }

    @ExceptionHandler(AppException::class)
    fun handleAppException(e: AppException): RespWrapper {
        if (SpringUtils.isDevProfile()) {
            e.printStackTrace()
        }
        logger.debug("App exception :", e.fillInStackTrace())
        return RespWrapper(e.info.getExpCode(), e.info.getExpMsg())
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(e: BadCredentialsException): RespWrapper {
        if (SpringUtils.isDevProfile()) {
            e.printStackTrace()
        }
        logger.debug("BadCredentialsException exception :", e.fillInStackTrace())
        val error = AuthError.NAME_PWD_ERROR
        return RespWrapper(error.code, error.msg)
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
