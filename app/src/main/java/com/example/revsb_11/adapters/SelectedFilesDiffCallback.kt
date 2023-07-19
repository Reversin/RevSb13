package com.example.revsb_11.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.revsb_11.data.SelectedFile

class SelectedFilesDiffCallback: DiffUtil.ItemCallback<SelectedFile>() {
    override fun areItemsTheSame(oldItem: SelectedFile, newItem: SelectedFile): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: SelectedFile, newItem: SelectedFile): Boolean {
        return oldItem == newItem
    }
}