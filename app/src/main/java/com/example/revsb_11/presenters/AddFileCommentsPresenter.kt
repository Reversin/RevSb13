package com.example.revsb_11.presenters

import android.content.ContentResolver
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.core.net.toUri
import com.example.revsb_11.R
import com.example.revsb_11.contracts.AddFileCommentsContract


class AddFileCommentsPresenter(
    private val view: AddFileCommentsContract.View,
    private val contentResolver: ContentResolver?
) : AddFileCommentsContract.Presenter {

    private lateinit var originalFile: String
    private lateinit var originalFileComment: String
    private lateinit var newFileComment: String
    private lateinit var fileFormat: String
    private val imageTypePrefix = "image/"

    override fun onScreenOpened(
        addFileCommentsArgument1: String,
        addFileCommentsArgument2: String
    ) {
        view.disableSaveButton()
        originalFile = addFileCommentsArgument1
        originalFileComment = addFileCommentsArgument2
        val fileUri = addFileCommentsArgument1.toUri()
        val fileName = view.processingLinkToFile(fileUri)
        if (fileName != null) {
            fileFormat = fileName.fileType
            view.setFileNameHint(fileName.fileName)

            if (fileName.fileType.startsWith(imageTypePrefix)) {
                val bitmapImage = view.getBitmapImageFromUri(fileUri)
                view.setBitmapImageInImageView(bitmapImage)
            } else {
                // TODO: Для обработки файлов раного формата (Реализовать после MVVM)
//                val fileIcon = view.getFileIcon(originalFile)
//                view.setDrawableImageInImageView(fileIcon)
            }
        }
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

    override fun onConsentSaveButtonClicked() =
        view.backToThePreviousFragmentWithChanges(originalFile, newFileComment)
}