package com.llh.basicadmin.common.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.logging.log4j.kotlin.Logging
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.time.Duration
import java.util.*
import javax.annotation.PostConstruct
import javax.xml.bind.DatatypeConverter

const val JWT_USERNAME = "username"

/**
 *
 * Created At 2020/11/11 13:20
 *
 * @author llh
 */
@Configuration
@PropertySource(value = ["classpath:/jwt.properties"])
@ConfigurationProperties(prefix = "jwt")
class JwtConfig {

    var accessToken: String = "authorization"

    var refreshToken: String = "refresh_token"

    lateinit var secretKey: String

    lateinit var accessTokenExpireTime: Duration

    lateinit var refreshTokenExpireTime: Duration

    lateinit var refreshTokenExpireAppTime: Duration

    lateinit var issuer: String

    @PostConstruct
    fun initUtil() {
        JwtTokenUtil.config = this
    }
}

object JwtTokenUtil : Logging {

    lateinit var config: JwtConfig

    /**
     * 生成 access_token
     *
     * @param subject JWT的主体:用户id
     * @param claims  存储在JWT里面的信息。请使用可变map类型！
     * @return access_token
     */
    fun generateAccessToken(subject: String, claims: Map<String, Any>,
                            secretKey: String = config.secretKey): String {

        if (claims is MutableMap) {
            checkClaimsType(claims, "access")
        }
        return generateToken(subject, claims, config.accessTokenExpireTime.toMillis(), secretKey)
    }

    /**
     * 从token中获取用户id。
     * 默认是subject字段存放用户id
     */
    fun extractUserId(token: String): String? {
        val claims = extractClaimsFrom(token)
        return claims?.subject
    }

    /**
     * 从token中获取用户名。
     */
    fun extractUsername(token: String): String? {
        val claims = extractClaimsFrom(token)
        return claims?.get(JWT_USERNAME) as String?
    }

    /**
     * 生成 refresh_token
     *
     * @param subject JWT的主体:用户id
     * @param claims  存储在JWT里面的信息。请使用可变map类型！
     * @return refresh_token
     */
    fun generateRefreshToken(subject: String, claims: Map<String, Any>): String {
        if (claims is MutableMap) {
            checkClaimsType(claims, "refresh")
        }
        return generateToken(subject, claims, config.refreshTokenExpireTime.toMillis())
    }

    private fun checkClaimsType(claims: Map<String, Any>?, type: String) {
        try {
            if (claims is MutableMap) { // 这里根本判断不了，也没有进行类型转换。淦！
                claims.put("typ", type) // 调用下put方法才能真正确定是否是可变类型。
            }
        } catch (e: Exception) {
            logger.error("请使用可变map类型转入参数！")
            e.printStackTrace()
        }
    }

    /**
     * 校验令牌可用性。
     * 令牌内容不为空并且没有过期，则令牌是可用的。
     * @return 令牌是否可用。true 令牌可用
     */
    fun validateToken(token: String?): Boolean {
        token ?: return false
        extractClaimsFrom(token) ?: return false
        return !isTokenExpired(token)

    }

    /**
     * 验证[token]是否已经过期。默认返回true，即过期。
     */
    fun isTokenExpired(token: String): Boolean {
        val isExpired = extractClaimsFrom(token)
            ?.expiration?.before(Date())
        return isExpired ?: true
    }

    /**
     * 获取token的签发时间.
     */
    fun extractIssuedTime(token: String): Long? {
        return extractClaimsFrom(token)
            ?.issuedAt?.time
    }

    /**
     *  获取token的剩余过期时间
     */
    fun extractRemainingTime(token: String): Long {
        val remaining = extractClaimsFrom(token)
            ?.expiration?.time
        return if (null == remaining) 0L
        else remaining - System.currentTimeMillis()
    }


    /**
     * 从[token]令牌中获取数据
     */
    fun extractClaimsFrom(token: String): Claims? {
        return try {
            Jwts.parser()
                .setSigningKey(DatatypeConverter.parseAnySimpleType(config.secretKey))
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            logger.error("Parse token error ,this token is $token")
            return null
        }
    }

    /**
     * 签发token
     *
     * @param issuer    签发人
     * @param subject   代表这个JWT的主体，即它的所有人。（用户id）
     * @param claims    存储在JWT里面的信息。如：用户的权限/角色信息。这里请使用可变Map！
     * @param ttlMillis 有效时间(毫秒)
     * @param secret    用于加密的字符串
     * @return 加密生成的token
     */
    fun generateToken(subject: String?, claims: Map<String, Any>?,
                      ttlMillis: Long, issuer: String = config.issuer,
                      secret: String = config.secretKey): String {
        val alg = SignatureAlgorithm.HS256
        // 用于加密的字符串转换为字节
        val signingKey = DatatypeConverter.parseAnySimpleType(secret)
        val builder = Jwts.builder()
        builder.setClaims(claims)

        // jwt的过期时间
        if (ttlMillis > 0) {
            val expMillis = ttlMillis + System.currentTimeMillis()
            builder.setExpiration(Date(expMillis))
        }
        return builder.signWith(alg, signingKey)
            .setSubject(subject) // 主体。 单独拿出去设置没设置上。T_T
            .setIssuer(issuer) //  签发者
            .setIssuedAt(Date()) //  签发时间
            .setClaims(claims) // 自定义数据
            .compact()
    }
}
