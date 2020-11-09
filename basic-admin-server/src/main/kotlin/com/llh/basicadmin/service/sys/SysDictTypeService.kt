package com.llh.basicadmin.service.sys

import com.llh.basicadmin.dao.SysDictTypes
import com.llh.basicadmin.model.SysDictType
import com.llh.basicadmin.pojo.SimpleQuery
import com.llh.basicadmin.pojo.vo.SysDictTypeVO
import com.llh.basicadmin.service.BasicService

/**
 *
 * Created At 2020/11/6 17:37
 *
 * @author llh
 */
interface SysDictTypeService : BasicService<SysDictType, SysDictTypes> {
    // FIXME 返回值类型
    fun pageAndQuery(pageVO: SimpleQuery<SysDictTypeVO>)
}
