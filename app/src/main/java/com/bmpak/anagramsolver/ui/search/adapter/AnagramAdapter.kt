package com.bmpak.anagramsolver.ui.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bmpak.anagramsolver.R
import com.bmpak.anagramsolver.ui.search.adapter.AnagramAdapter.BaseViewHolder

class AnagramAdapter : RecyclerView.Adapter<BaseViewHolder>() {

  private val INVALID_POSITION = -1

  val itemList: MutableList<AnagramItem> = mutableListOf()

  override fun getItemCount(): Int = itemList.size
  override fun getItemViewType(position: Int): Int = getItemAt(position)?.getItemViewType() ?: -1

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return when (viewType) {
      AnagramItem.ACTUAL_ANAGRAM -> ActualAnagramViewHolder(inflater, parent)
      else -> throw IllegalArgumentException("$viewType is not supported by this adapter.")
    }
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    val item: AnagramItem = getItemAt(position) ?: return
    holder.bind(item, position)
  }

  fun setItems(items: List<AnagramItem>) {
    itemList.clear()
    itemList.addAll(items)
    notifyDataSetChanged()
  }

  fun addItems(items: List<AnagramItem>) {
    val prevSize = itemCount
    itemList.addAll(items)
    notifyItemRangeInserted(prevSize, items.size)
  }

  fun addItems(items: List<AnagramItem>, position: Int) {
    if (position == INVALID_POSITION) {
      return
    }

    itemList.addAll(position, items)
    notifyItemRangeInserted(position, items.size)
  }

  fun removeItem(position: Int) {
    if (position == INVALID_POSITION) {
      return
    }

    itemList.removeAt(position)
    notifyItemRemoved(position)
  }

  fun removeAll() {
    itemList.clear()
    notifyDataSetChanged()
  }

  fun getItemAt(position: Int): AnagramItem? = itemList.getOrNull(position)

  fun clearItems() {
    itemList.clear()
    notifyDataSetChanged()
  }

  abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: AnagramItem, position: Int)
  }

  class ActualAnagramViewHolder(itemView: View) : BaseViewHolder(itemView) {

    constructor(inflater: LayoutInflater, parent: ViewGroup) :
        this(inflater.inflate(R.layout.item_anagram_result, parent, false))

    private val titleEditText: TextView = itemView.findViewById(R.id.item_anagram_result_title)

    override fun bind(item: AnagramItem, position: Int) {
      item as ActualAnagramItem
      titleEditText.text = item.anagram.word
    }
  }

}
