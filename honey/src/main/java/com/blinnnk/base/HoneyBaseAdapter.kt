package com.blinnnk.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

@Suppress("UNCHECKED_CAST")
abstract class HoneyBaseAdapter<DataType, R: View>
  : RecyclerView.Adapter<HoneyBaseAdapter<DataType, R>.ViewHolder>() {

  abstract val dataSet: ArrayList<DataType>
  abstract fun generateCell(context: Context): R
  abstract fun R.bindCell(data: DataType, position: Int)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(generateCell(parent.context))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    (holder.itemView as? R)?.apply { bindCell(dataSet[position], position) }
  }

  override fun getItemCount(): Int = dataSet.size

  inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView)
}