package com.llh.basicadmin.common.exception

import com.llh.basicadmin.common.exception.code.ExpInfo

/**
 *
 * Created At 2020/11/10 14:02
 *
 * @author llh
 */
class AppException(val info: ExpInfo) : Exception() {

}
