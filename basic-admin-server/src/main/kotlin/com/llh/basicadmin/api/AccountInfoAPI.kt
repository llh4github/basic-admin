package com.llh.basicadmin.api

import com.llh.basicadmin.pojo.AccountVO
import com.llh.basicadmin.pojo.RespWrapper
import com.llh.basicadmin.pojo.okResponse
import com.llh.basicadmin.service.sys.AccountService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 *
 * Created At 2020/11/10 13:42
 *
 * @author llh
 */
@RestController
@RequestMapping("account")
@Api(tags = ["帐户信息的API"])
class AccountInfoAPI {

    @Autowired
    private lateinit var accountService: AccountService

    @PostMapping("register")
    @ApiOperation("注册帐户")
    fun register(@RequestBody account: AccountVO): RespWrapper {
        val operation: Boolean = accountService.register(account)
        return okResponse(operation)
    }

    @PostMapping("login")
    @ApiOperation("登录")
    fun login(@RequestBody account: AccountVO): RespWrapper {
        val operation = accountService.login(account)
        return okResponse(operation)
    }
}
