package com.llh.basicadmin.common.validation

import javax.validation.GroupSequence

/**
 * 基本操作的验证类
 *
 * 此包内的接口仅作为”标识符“，不应当有具体的方法
 * Created At 2020/10/28 17:08
 *
 * @author llh
 */

interface AddOperate
interface UpdateOperate
interface QueryOperate

@GroupSequence(value = [AddOperate::class, UpdateOperate::class])
interface AddAndUpdate
