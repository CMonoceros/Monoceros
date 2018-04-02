package com.zjmzyx.monoceros.library.base.ui.holder

import android.util.SparseArray

/**
 *
 * @author zjm
 * @date 2017/8/17
 */

class BaseHolderContainerBuilder<T>(private val sparseArray: SparseArray<T>, private val itemTypeId: Int, private val arrayIndex: Int) {

    private var isFoot = false
    private var footTypeId: Int = 0
    private var isHead = false
    private var headTypeId: Int = 0

    fun setFoot(foot: Boolean, footTypeId: Int): BaseHolderContainerBuilder<*> {
        isFoot = foot
        this.footTypeId = footTypeId
        return this
    }

    fun setHead(head: Boolean, headTypeId: Int): BaseHolderContainerBuilder<*> {
        isHead = head
        this.headTypeId = headTypeId
        return this
    }

    fun build(): BaseHolderContainer<*> {
        return if (isHead && isFoot) {
            BaseHolderContainer(sparseArray, itemTypeId, isHead, headTypeId, isFoot, footTypeId, arrayIndex)
        } else if (isHead) {
            BaseHolderContainer(sparseArray, itemTypeId, isHead, headTypeId, arrayIndex)
        } else if (isFoot) {
            BaseHolderContainer(sparseArray, itemTypeId, isFoot, footTypeId, arrayIndex, false)
        } else {
            BaseHolderContainer(sparseArray, itemTypeId, arrayIndex)
        }
    }
}
