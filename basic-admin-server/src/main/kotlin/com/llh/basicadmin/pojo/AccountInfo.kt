package com.llh.basicadmin.pojo

import com.llh.basicadmin.model.SysUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


/**
 * 帐户信息
 * Created At 2020/11/9 17:39
 *
 * @author llh
 */
class AccountInfo(private val user: SysUser) : UserDetails {

    val id: Int? = user.id


    private val authoritySet = mutableSetOf<GrantedAuthority>()


    fun addAuthorities(authority: List<GrantedAuthority>) {
        if (authority.isEmpty()) return
        authoritySet.addAll(authority)
    }

    override fun getAuthorities(): Set<GrantedAuthority> {
        return authoritySet
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.username
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

data class AccountVO(
    val username: String,
    val password: String,
    val id: Int?,
)

data class AuthTokenVO(
    val access: String,
    val refresh: String,
)
