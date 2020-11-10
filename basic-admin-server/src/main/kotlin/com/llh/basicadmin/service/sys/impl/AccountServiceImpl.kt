package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.common.exception.AppException
import com.llh.basicadmin.common.exception.code.UserError
import com.llh.basicadmin.common.util.PwdUtil
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.pojo.AccountInfo
import com.llh.basicadmin.pojo.AccountVO
import com.llh.basicadmin.service.sys.AccountService
import com.llh.basicadmin.service.sys.SysAuthorityService
import com.llh.basicadmin.service.sys.SysRoleService
import com.llh.basicadmin.service.sys.SysUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AccountServiceImpl : AccountService {
    @Autowired
    private lateinit var sysUserService: SysUserService

    @Autowired
    private lateinit var sysRoleService: SysRoleService

    @Autowired
    private lateinit var sysAuthorityService: SysAuthorityService

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrBlank()) throw UsernameNotFoundException("用户名为空")
        val model = sysUserService.findByUsername(username)
            ?: throw UsernameNotFoundException("没有找到指定用户名（$username）的用户信息")
        val info = AccountInfo(model)
        val rList = sysRoleService.getListByUserId(model.id)
        val aList = sysAuthorityService.getListByRoleIds(rList.map { it.id })
        info.addAuthorities(rList)
        info.addAuthorities(aList)
        return info
    }

    override fun register(account: AccountVO): Boolean {
        if (sysUserService.noHasUsername(account.username))
            throw AppException(UserError.USERNAME_DUPLICATE)
        val model = SysUser {
            username = account.username
            password = PwdUtil.encode(account.password)
            createdBy = 2
        }
        sysUserService.save(model)
        return true
    }

    override fun login(account: AccountVO): Boolean {

        return true
    }
}