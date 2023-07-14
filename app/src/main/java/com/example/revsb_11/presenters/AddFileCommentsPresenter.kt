package com.example.revsb_11.presenters

import android.content.ContentResolver
import androidx.core.net.toUri
import com.example.revsb_11.contracts.AddFileCommentsContract


class AddFileCommentsPresenter(
    private val view: AddFileCommentsContract.View,
    private val contentResolver: ContentResolver?
) : AddFileCommentsContract.Presenter {

    private lateinit var originalFile: String
    private lateinit var originalFileComment: String
    private lateinit var newFileComment: String
    private lateinit var fileFormat: String

    override fun onScreenOpened(
        addFileCommentsArgument1: String,
        addFileCommentsArgument2: String
    ) {
        view.disableSaveButton()
        originalFile = addFileCommentsArgument1
        originalFileComment = addFileCommentsArgument2
        val fileUri = addFileCommentsArgument1.toUri()
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
        view.setFileComments(originalFileComment)
    }

    override fun onTextHasBeenChanged(editFileCommentText: String) {
        newFileComment = editFileCommentText
        if (editFileCommentText != originalFileComment) {
            view.enableSaveButton()
        } else {
            view.disableSaveButton()
        }
    }

    override fun onSaveButtonClicked() {
        view.showConfirmationOfTheChanges()
    }

    override fun onConsentSaveButtonClicked(changedFileName: String) {
        view.backToThePreviousFragmentWithChanges(originalFile, changedFileName)
    }
}