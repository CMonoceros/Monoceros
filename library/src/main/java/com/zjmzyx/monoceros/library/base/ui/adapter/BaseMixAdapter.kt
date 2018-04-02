package com.zjmzyx.monoceros.library.base.ui.adapter

import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup


import com.zjmzyx.monoceros.library.base.ui.holder.BaseHolderContainer
import com.zjmzyx.monoceros.library.base.ui.holder.BaseHolderContainerInterface

import java.util.HashSet

/**
 *
 * @author zjm
 * @date 2017/8/17
 */

class BaseMixAdapter(baseHolderSparseArray: SparseArray<BaseHolderContainer<*>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ERROR_TYPE_ID = -1

    private var baseHolderSparseArray = SparseArray<BaseHolderContainer<*>>()
    private val holderTypeSet = HashSet<Int>()
    private var itemCount = 0
    private var itemType = 0

    init {
        this.baseHolderSparseArray = baseHolderSparseArray
        notifyItemCountChanged()
    }

    fun getPositionHolderContainer(position: Int): BaseHolderContainer<*>? {
        val index = 0
        var baseHolderContainer: BaseHolderContainer<*>
        for (i in 0 until baseHolderSparseArray.size()) {
            baseHolderContainer = baseHolderSparseArray.get(i)
            if (position >= baseHolderContainer.startPosition &&
                    position <= baseHolderContainer.endPosition) {
                return baseHolderContainer
            }
        }
        return null
    }

    private fun notifyItemCountChanged() {
        itemCount = 0
        itemType = 0
        holderTypeSet.clear()
        var baseHolderContainer: BaseHolderContainer<*>
        for (i in 0 until baseHolderSparseArray.size()) {
            baseHolderContainer = baseHolderSparseArray.get(i)
            baseHolderContainer.startPosition = itemCount
            if (baseHolderContainer.isHead) {
                itemCount++
                itemType++
                holderTypeSet.add(baseHolderContainer.headTypeId)
            }
            if (baseHolderContainer.sparseArraySize != 0) {
                itemCount += baseHolderContainer.sparseArraySize
                itemType++
                holderTypeSet.add(baseHolderContainer.itemTypeId)
            }
            if (baseHolderContainer.isFoot) {
                itemCount++
                itemType++
                holderTypeSet.add(baseHolderContainer.footTypeId)
            }
            baseHolderContainer.endPosition = itemCount - 1
        }
        if (itemType != holderTypeSet.size) {
            try {
                throw Exception("存在相同值Holder类型")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun notifyItemCountRangeChanged(holder: BaseHolderContainer<*>) {
        itemCount = holder.startPosition
        var baseHolderContainer: BaseHolderContainer<*>
        for (i in holder.arrayIndex until baseHolderSparseArray.size()) {
            baseHolderContainer = baseHolderSparseArray.get(i)
            baseHolderContainer.startPosition = itemCount
            if (baseHolderContainer.isHead) {
                itemCount++
            }
            itemCount += baseHolderContainer.sparseArraySize
            if (baseHolderContainer.isFoot) {
                itemCount++
            }
            baseHolderContainer.endPosition = itemCount - 1
        }
    }

    fun notifyBaseHolderChanged() {
        notifyItemCountChanged()
        notifyDataSetChanged()
    }

    fun notifyBaseHolderRangeChanged(baseHolderContainer: BaseHolderContainer<*>) {
        notifyItemCountRangeChanged(baseHolderContainer)
        notifyItemRangeChanged(baseHolderContainer.startPosition, itemCount)
    }

    fun notifyBaseHolderInsertChanged(baseHolderContainer: BaseHolderContainer<*>) {
        val endPosition = baseHolderContainer.endPosition
        notifyBaseHolderRangeChanged(baseHolderContainer)
        notifyItemInserted(endPosition)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        var baseHolderContainer: BaseHolderContainer<*>
        for (i in 0 until baseHolderSparseArray.size()) {
            baseHolderContainer = baseHolderSparseArray.get(i)
            if (baseHolderContainer.headTypeId == viewType ||
                    baseHolderContainer.footTypeId == viewType ||
                    baseHolderContainer.itemTypeId == viewType) {
                if (baseHolderContainer is BaseHolderContainerInterface) {
                    return (baseHolderContainer as BaseHolderContainerInterface)
                            .onCreateViewHolder(parent, viewType)
                } else {
                    try {
                        throw Exception("该" + baseHolderContainer.toString() +
                                "没有实现BaseHolderInterface")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    return object : RecyclerView.ViewHolder(null) {
                        override fun toString(): String {
                            return super.toString()
                        }
                    }
                }
            }
        }
        try {
            throw Exception("没有找到 $viewType 类型Holder")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return object : RecyclerView.ViewHolder(null) {
            override fun toString(): String {
                return super.toString()
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var baseHolderContainer: BaseHolderContainer<*>
        for (i in 0 until baseHolderSparseArray.size()) {
            baseHolderContainer = baseHolderSparseArray.get(i)
            if (baseHolderContainer.startPosition <= position &&
                    position <= baseHolderContainer.endPosition) {
                if (baseHolderContainer is BaseHolderContainerInterface) {
                    (baseHolderContainer as BaseHolderContainerInterface)
                            .onBindViewHolder(holder,
                                    position - baseHolderContainer.startPosition)
                } else {
                    try {
                        throw Exception("该" + baseHolderContainer.toString() +
                                "没有实现BaseHolderInterface")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
                return
            }
        }
        try {
            throw Exception("没有找到 $position 位置Holder")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        var baseHolderContainer: BaseHolderContainer<*>
        for (i in 0 until baseHolderSparseArray.size()) {
            baseHolderContainer = baseHolderSparseArray.get(i)
            if (baseHolderContainer.isHead && position == baseHolderContainer.startPosition) {
                return baseHolderContainer.headTypeId
            }
            if (baseHolderContainer.isFoot && position == baseHolderContainer.endPosition) {
                return baseHolderContainer.footTypeId
            }
            if (position >= baseHolderContainer.startPosition &&
                    position <= baseHolderContainer.endPosition) {
                return baseHolderContainer.itemTypeId
            }
        }
        try {
            throw Exception("没有找到 $position 位置Holder")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ERROR_TYPE_ID
    }
}
