package com.zjmzyx.monoceros.library.base.ui.holder

import android.util.SparseArray

/**
 *
 * @author zjm
 * @date 2017/8/17
 */

class BaseHolderContainer<T> {

    var startPosition: Int = 0
    var endPosition: Int = 0
    var isFoot = false
        private set
    var footTypeId: Int = 0
        private set
    var isHead = false
        private set
    var headTypeId: Int = 0
        private set
    var sparseArray: SparseArray<T>
        private set
    var itemTypeId: Int = 0
        private set
    var arrayIndex: Int = 0
        private set

    val sparseArraySize: Int
        get() = sparseArray.size()

    constructor(sparseArray: SparseArray<T>, itemTypeId: Int, arrayIndex: Int) {
        this.sparseArray = sparseArray
        this.itemTypeId = itemTypeId
        this.arrayIndex = arrayIndex
    }

    constructor(sparseArray: SparseArray<T>, itemTypeId: Int, isFoot: Boolean, footTypeId: Int, arrayIndex: Int, flag: Boolean) {
        this.sparseArray = sparseArray
        this.itemTypeId = itemTypeId
        this.isFoot = isFoot
        this.footTypeId = footTypeId
        this.arrayIndex = arrayIndex
    }

    constructor(sparseArray: SparseArray<T>, itemTypeId: Int, isHead: Boolean, headTypeId: Int, arrayIndex: Int) {
        this.sparseArray = sparseArray
        this.itemTypeId = itemTypeId
        this.isHead = isHead
        this.headTypeId = headTypeId
        this.arrayIndex = arrayIndex
    }

    constructor(sparseArray: SparseArray<T>, itemTypeId: Int, isHead: Boolean, headTypeId: Int, isFoot: Boolean, footTypeId: Int, arrayIndex: Int) {
        this.sparseArray = sparseArray
        this.itemTypeId = itemTypeId
        this.isHead = isHead
        this.headTypeId = headTypeId
        this.isFoot = isFoot
        this.footTypeId = footTypeId
        this.arrayIndex = arrayIndex
    }

    fun notifyBaseHolderChanged(sparseArray: SparseArray<T>) {
        this.sparseArray = sparseArray
    }

    fun setFoot(foot: Boolean, footTypeId: Int) {
        isFoot = foot
        this.footTypeId = footTypeId
    }

    fun setHead(head: Boolean, headTypeId: Int) {
        isHead = head
        this.headTypeId = headTypeId
    }
}
