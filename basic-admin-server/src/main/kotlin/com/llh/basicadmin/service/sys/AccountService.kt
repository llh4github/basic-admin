package com.llh.basicadmin.service.sys

import com.llh.basicadmin.pojo.AccountVO
import org.springframework.security.core.userdetails.UserDetailsService

/**
 * 帐户信息
 * Created At 2020/11/10 11:23
 *
 * @author llh
 */
interface AccountService : UserDetailsService {
    fun register(account: AccountVO): Boolean
    fun login(account: AccountVO): Boolean

}
