package com.zjmzyx.monoceros.library.base.model.bean.base

/**
 *
 * @author zjm
 * @date 2017/12/29
 */

interface BaseBeanConvert<T> {
    fun convert(vararg types: String): T
}
