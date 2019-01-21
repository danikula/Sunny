package com.danikula.sunny.ui

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import com.danikula.sunny.R

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
fun RecyclerView.useDefaultDivider() {
    this.let {
        val divider = DividerItemDecoration(context, LinearLayout.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.list_divider)!!)
        addItemDecoration(divider)
    }
}

fun <VH : RecyclerView.ViewHolder> RecyclerView.setup(recyclerViewAdapter: RecyclerView.Adapter<VH>) {
    this.let {
        useDefaultDivider()
        setHasFixedSize(true)
        adapter = recyclerViewAdapter
    }
}
