package com.llh.basicadmin.api

import com.llh.basicadmin.service.sys.SysUserService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * SysUserAPI
 *
 * CreatedAt: 2020-11-04 22:06
 *
 * @author llh
 */
@RestController
@RequestMapping("sys/user")
@Api(tags = ["用户信息的API"])
class SysUserAPI {
    @Autowired
    private lateinit var sysUserService: SysUserService


}
