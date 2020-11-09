package com.llh.basicadmin.pojo

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 *
 * Created At 2020/11/9 17:39
 *
 * @author llh
 */
class AccountInfo : UserDetails {
    override fun getAuthorities(): Set<AuthorityWrapper> {
        return emptySet<AuthorityWrapper>()
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return ""
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}

/**
 * 权限信息包装类
 *
 * Security框架需要
 */
class AuthorityWrapper : GrantedAuthority {
    override fun getAuthority(): String {
        return ""
    }
}
