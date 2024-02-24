package com.revsb_11.adapters

import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.revsb_11.models.dataclasses.SelectedFile
import com.revsb_11.databinding.ItemLayoutBinding

open class SelectedFilesViewHolder(
    binding: ItemLayoutBinding,
    private val onEditButtonClicked: (SelectedFile) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private val composeView = itemView as ComposeView

    fun bind(selectedFile: SelectedFile) {

//        composeView.setContent {
//            SelectedFileList(modifier = Modifier, uiState = SelectedFilesUIState(
//                files = listOf(
//                    SelectedFile(
//                        filePath = "Yes",
//                        fileSize = "Yes",
//                        longTermPath = "Yes",
//                        fileComments = "Yes"
//                    ),
//                    SelectedFile(
//                        filePath = "No",
//                        fileSize = "No",
//                        longTermPath = "No",
//                        fileComments = "No",
//                    )
//                ),
//            ),
//                onSelectedFileClicked = {},
//                onEditButtonClicked = {selectedFile -> onEditButtonClicked(selectedFile)})
//        }
//
//
//        itemView.setOnClickListener {
//            val uri = Uri.parse(selectedFile.longTermPath)
//            val extension = MimeTypeMap.getFileExtensionFromUrl(selectedFile.longTermPath)
//            val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
//            val intent = Intent().apply {
//                action = Intent.ACTION_VIEW
//                setDataAndType(uri, mimeType)
//                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            }
//            it.context.startActivity(intent)
//        }
    }
}