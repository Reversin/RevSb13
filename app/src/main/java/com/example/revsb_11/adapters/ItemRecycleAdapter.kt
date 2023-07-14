package com.example.revsb_11.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.data.SelectedFile
import com.example.revsb_11.databinding.ItemLayoutBinding

class ItemRecycleAdapter(
    private val onEditButtonClicked: (SelectedFile) -> Unit,
    private val onSwipeToDelete: (SelectedFile) -> Unit
) : RecyclerView.Adapter<ItemRecycleAdapter.ItemViewHolder>() {
    private var selectedFiles: List<SelectedFile> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewTypr: Int): ItemViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onEditButtonClicked)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) =
        holder.bind(selectedFiles[position])

    fun attachSwipeToDelete(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                removeItem(position)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int =
        selectedFiles.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<SelectedFile>) {
        this.selectedFiles = items
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        val updatedList = selectedFiles.toMutableList()
        onSwipeToDelete(selectedFiles[position])
        updatedList.removeAt(position)
        selectedFiles = updatedList
        notifyItemRemoved(position)
    }


    class ItemViewHolder(
        binding: ItemLayoutBinding,
        private val onEditButtonClicked: (SelectedFile) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private val fileTextView: TextView = binding.fileTextView
        private val fileSizeTextView: TextView = binding.fileSizeTextView
        private val fileCommentsTextView:TextView = binding.fileCommentsTextView
        private val editButton: ImageButton = binding.editFileButton

        fun bind(selectedFile: SelectedFile) {
            fileTextView.text = selectedFile.filePath
            fileSizeTextView.text = selectedFile.fileSize
            fileCommentsTextView.text = selectedFile.fileComments

            editButton.setOnClickListener {
                onEditButtonClicked(selectedFile)
            }
        }
    }
}