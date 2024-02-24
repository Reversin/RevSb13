package com.revsb_11.adapters

import androidx.recyclerview.widget.DiffUtil
import com.revsb_11.models.dataclasses.SelectedFile

class SelectedFilesDiffCallback: DiffUtil.ItemCallback<SelectedFile>() {
    override fun areItemsTheSame(oldItem: SelectedFile, newItem: SelectedFile): Boolean {
        return oldItem.longTermPath == newItem.longTermPath
    }

    override fun areContentsTheSame(oldItem: SelectedFile, newItem: SelectedFile): Boolean {
        return oldItem == newItem
    }
}