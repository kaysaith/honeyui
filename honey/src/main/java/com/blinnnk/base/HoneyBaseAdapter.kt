package com.blinnnk.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * Single Cell Adapter
 */
@Suppress("UNCHECKED_CAST")
abstract class HoneyBaseAdapter<DataType, R : View>
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

/**
 * Adapter With Header, Cell, Footer
 */
@Suppress("UNCHECKED_CAST")
abstract class HoneyBaseAdapterWithHeaderAndFooter<D, out H : View, C : View, out F : View>
    : RecyclerView.Adapter<HoneyBaseAdapterWithHeaderAndFooter<D, H, C, F>.ViewHolder>() {

    abstract val dataSet: ArrayList<D>

    abstract fun generateHeader(context: Context): H
    abstract fun generateCell(context: Context): C
    abstract fun generateFooter(context: Context): F

    abstract fun C.bindCell(data: D, position: Int)

    enum class CellType(val value: Int) {
        Header(0), Cell(1), Footer(2)
    }

    private var headerView: H? = null
    private var normalCell: C? = null
    private var footer: F? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var viewHolder: ViewHolder? = null
        when (viewType) {
            CellType.Header.value -> {
                headerView = generateHeader(parent.context)
                headerView?.let { viewHolder = ViewHolder(it) }
            }

            CellType.Cell.value -> {
                normalCell = generateCell(parent.context)
                normalCell?.let { viewHolder = ViewHolder(it) }
            }

            CellType.Footer.value -> {
                footer = generateFooter(parent.context)
                footer?.let { viewHolder = ViewHolder(it) }
            }

        }
        return viewHolder!!
    }

    override fun getItemViewType(position: Int): Int = when (position) {
        0 -> CellType.Header.value
        dataSet.size + 1 -> CellType.Footer.value
        else -> CellType.Cell.value
    }

    override fun getItemCount(): Int = dataSet.size + 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cellType = getItemViewType(position)
        when (cellType) {
            CellType.Header.value -> {
                // 赋值
            }
            CellType.Cell.value -> {
                // 赋值
                (holder.itemView as? C)?.apply { bindCell(dataSet[position - 1], position) }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}