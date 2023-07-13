package com.example.revsb_11.presenters

import android.content.ContentResolver
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import com.example.revsb_11.contracts.AddFileCommentsContract
import com.example.revsb_11.data.FileName
import com.example.revsb_11.data.WorkingWithFiles


class AddFileCommentsPresenter(
    private val view: AddFileCommentsContract.View,
    private val contentResolver: ContentResolver?
) : AddFileCommentsContract.Presenter {

    private lateinit var originalFile: String
    private lateinit var fileFormat: String

    override fun onScreenOpened(changeFileNameArgument: String) {
        view.disableSaveButton()
        originalFile = changeFileNameArgument
        val fileUri = changeFileNameArgument.toUri()
//        fileUri.let { selectedUri ->
//            val file =
//                contentResolver?.let { WorkingWithFiles().getFileNameFromUri(it, selectedUri) }
//            fileFormat = ".${file?.fileType?.substringAfter("/")}"
//            val indexFormat = file?.fileName?.lastIndexOf(dot)
//            val fileName = indexFormat?.let { file.fileName.substring(0, it) }
//            if (fileName != null) {
//                originalFileName = fileName
//                view.setText(fileName, fileFormat)
//            }
        val fileName = view.processingLinkToFile(fileUri)
        if (fileName != null) {
            view.setFileNameHint(fileName.fileName)
        }

        view.setImageInImageView(fileUri)
    }

    override fun textHasBeenChanged(editFileNameText: String) {
//        if (editFileNameText != originalFileName) {
//            view.enableSaveButton()
//        } else {
//            view.disableSaveButton()
//        }
    }

    override fun onSaveButtonClicked() {
        view.showAlertDialog()
    }

    override fun onConsentSaveButtonClicked(changedFileName: String) {
        view.backToThePreviousFragmentWithChanges(originalFile, "$changedFileName,$fileFormat" )
    }

    companion object {
        private const val dot = "."
    }
}