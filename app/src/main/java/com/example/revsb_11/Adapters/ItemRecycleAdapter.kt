package com.example.revsb_11.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.Data.Item
import com.example.revsb_11.R

class ItemRecycleAdapter (private val items: MutableList<Item>) : RecyclerView.Adapter<ItemRecycleAdapter.ItemViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewTypr: Int): ItemViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
      return ItemViewHolder(view)
  }

    override fun onBindViewHolder(holder: ItemRecycleAdapter.ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Item) {
        items.add(item)

        notifyItemInserted(items.size - 1)

    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val fileTextView: TextView = itemView.findViewById(R.id.fileTextView)
        private val editButton: ImageButton = itemView.findViewById(R.id.editFileButton)


        fun bind(item: Item) {
            fileTextView.text = item.fileName

            editButton.setOnClickListener {
                deleteItem(item)
            }
        }

        private fun deleteItem(item: Item) {
            val position  = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

}