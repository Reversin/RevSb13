package com.example.revsb_11.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.data.Data
import com.example.revsb_11.databinding.ItemLayoutBinding

class ItemRecycleAdapter(
    private val onEditButtonClicked: (Data) -> Unit
) : RecyclerView.Adapter<ItemRecycleAdapter.ItemViewHolder>() {
    private var items: List<Data> = emptyList()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewTypr: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onEditButtonClicked)
    }
    
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(items[position])
    
    override fun getItemCount(): Int =
        items.size

//    @SuppressLint("NotifyDataSetChanged")
//    fun addItem(item: Data) {
//        model.saveItem(item)
//        notifyDataSetChanged()
//    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Data>) {
        this.items = items
        notifyDataSetChanged()
    }

//        setItems(listOf(item))
//    private fun setItems(items: List<Data>) {
//        model.saveItems(items)
//        notifyItemRangeInserted(0, items.size)
//        notifyDataSetChanged()
//    }
    
    class ItemViewHolder(
        binding: ItemLayoutBinding,
        private val onEditButtonClicked: (Data) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        
        private val fileTextView: TextView = binding.fileTextView
        private val fileSizeTextView: TextView = binding.fileSizeTextView
        private val editButton: ImageButton = binding.editFileButton
        
        fun bind(item: Data) {
            fileTextView.text  = item.filePath
            fileSizeTextView.text = item.fileSize
            editButton.setOnClickListener {
                onEditButtonClicked(item)
            }
        }
    }
}