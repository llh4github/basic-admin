package com.llh.basicadmin.common.constant

/**
 *
 * Created At 2020/11/20 16:30
 *
 * @author llh
 */

/**
 * 角色名必须以 ROLE_ 开头。
 *
 * 这个是SpringSecurity框架的要求。
 * 他们认为“角色”和“权限”都是字符串，没有单独用字段来存放，只好以前缀来区分。
 */
const val ROLE_PREFIX = "ROLE_"
