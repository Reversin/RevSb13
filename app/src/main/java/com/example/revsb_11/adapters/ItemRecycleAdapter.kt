package com.example.revsb_11.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.data.Item
import com.example.revsb_11.R
import com.example.revsb_11.contracts.FirstFragmentContract
import com.example.revsb_11.databinding.ItemLayoutBinding

class ItemRecycleAdapter(
    private val onEditButtonClicked: (Item) -> Unit
) : RecyclerView.Adapter<ItemRecycleAdapter.ItemViewHolder>() {
    private var items: List<Item> = emptyList()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewTypr: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onEditButtonClicked)
    }
    
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(items[position])
    
    override fun getItemCount(): Int =
        items.size

//    @SuppressLint("NotifyDataSetChanged")
//    fun addItem(item: Item) {
//        model.saveItem(item)
//        notifyDataSetChanged()
//    }
    
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

//        setItems(listOf(item))
//    private fun setItems(items: List<Item>) {
//        model.saveItems(items)
//        notifyItemRangeInserted(0, items.size)
//        notifyDataSetChanged()
//    }
    
    class ItemViewHolder(
        binding: ItemLayoutBinding,
        private val onEditButtonClicked: (Item) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        
        private val fileTextView: TextView = binding.fileTextView
        private val editButton: ImageButton = binding.editFileButton
        
        fun bind(item: Item) {
            val fileInfo = item.extraInfo
            fileTextView.text = "$fileInfo"
            editButton.setOnClickListener {
                onEditButtonClicked(item)
            }
        }
    }
}