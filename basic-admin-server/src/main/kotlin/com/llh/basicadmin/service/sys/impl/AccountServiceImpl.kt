package com.llh.basicadmin.service.sys.impl

import com.llh.basicadmin.common.constant.UserInfoFunCache
import com.llh.basicadmin.common.exception.AppException
import com.llh.basicadmin.common.exception.code.AuthError
import com.llh.basicadmin.common.exception.code.UserError
import com.llh.basicadmin.common.util.JwtTokenUtil
import com.llh.basicadmin.common.util.PwdUtil
import com.llh.basicadmin.model.SysUser
import com.llh.basicadmin.pojo.AccountInfo
import com.llh.basicadmin.pojo.AccountVO
import com.llh.basicadmin.pojo.AuthTokenVO
import com.llh.basicadmin.service.cache.UserCacheService
import com.llh.basicadmin.service.sys.AccountService
import com.llh.basicadmin.service.sys.SysAuthorityService
import com.llh.basicadmin.service.sys.SysRoleService
import com.llh.basicadmin.service.sys.SysUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
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

    @Autowired
    private lateinit var userCacheService: UserCacheService


    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Cacheable(UserInfoFunCache.prefix + "loadUserByUsername")
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

    override fun login(account: AccountVO): AuthTokenVO {
        val usernameAuthentication = UsernamePasswordAuthenticationToken(
            account.username, account.password)
        val authenticate = authenticationManager.authenticate(usernameAuthentication)

        SecurityContextHolder.getContext().authentication = authenticate
        val vo = authenticate.principal as AccountInfo // 从数据库里拿出来的 参考loadUserByUsername的返回类型
        val tokenVo = AuthTokenVO(
            access = genAccessToken(vo),
            refresh = genRefreshToken(vo)
        )
        userCacheService.cacheLoginInfo(tokenVo, vo)
        return tokenVo
    }
}

/* 生成 access_token  */
private fun genAccessToken(info: AccountInfo): String {
    return genToken(info, JwtTokenUtil::generateAccessToken)
}

/* 生成 refresh_token  */
private fun genRefreshToken(info: AccountInfo): String {
    return genToken(info, JwtTokenUtil::generateRefreshToken)
}

/* 生成 token */
private fun genToken(info: AccountInfo,
                     method: (String, Map<String, Any>) -> String): String {

    info.id ?: throw AppException(AuthError.USER_NOT_EXIST)
    /* 不宜添加过多的信息 */
    val m = mutableMapOf<String, Any>(
        "username" to info.username,
    )
    return method("${info.id}", m)
}
