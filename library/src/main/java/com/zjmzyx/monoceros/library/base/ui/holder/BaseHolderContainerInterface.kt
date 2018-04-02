package com.zjmzyx.monoceros.library.base.ui.holder

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 *
 * @author zjm
 * @date 2017/8/17
 */

interface BaseHolderContainerInterface {
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
}
