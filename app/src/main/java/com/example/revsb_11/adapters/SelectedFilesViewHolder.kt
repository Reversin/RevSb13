package com.example.revsb_11.adapters

import android.content.Intent
import android.net.Uri
import android.webkit.MimeTypeMap
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.revsb_11.dataclasses.SelectedFile
import com.example.revsb_11.databinding.ItemLayoutBinding

open class SelectedFilesViewHolder(
    binding: ItemLayoutBinding,
    private val onEditButtonClicked: (SelectedFile) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private val fileTextView: TextView = binding.fileTextView
    private val fileSizeTextView: TextView = binding.fileSizeTextView
    private val fileCommentsTextView: TextView = binding.fileCommentsTextView
    private val editButton: ImageButton = binding.editFileButton

    fun bind(selectedFile: SelectedFile) {
        fileTextView.text = selectedFile.filePath
        fileSizeTextView.text = selectedFile.fileSize
        fileCommentsTextView.text = selectedFile.fileComments
        editButton.setOnClickListener {
            onEditButtonClicked(selectedFile)
        }
        itemView.setOnClickListener {
            val uri = Uri.parse(selectedFile.longTermPath)
            val extension = MimeTypeMap.getFileExtensionFromUrl(selectedFile.longTermPath)
            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                setDataAndType(uri, mimeType)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            it.context.startActivity(intent)
        }
    }
}