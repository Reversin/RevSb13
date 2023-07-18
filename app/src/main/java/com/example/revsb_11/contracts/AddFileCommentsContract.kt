package com.example.revsb_11.contracts

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.example.revsb_11.data.FileName

interface AddFileCommentsContract {

    interface View {
        fun setBitmapImageInImageView(bitmap: Bitmap?)
        fun setDrawableImageInImageView(drawable: Drawable?)
        fun showConfirmationOfTheChangesDialog()
        fun backToThePreviousFragmentWithChanges()
        fun disableSaveButton()
        fun enableSaveButton()
        fun setFileNameHint(fileName: String?)
        fun setFileComment(fileComments: String)
        fun processingLinkToFile(fileUri: Uri): FileName?
        fun processingImageFile(fileUri: Uri): Bitmap
        fun getBitmapImageFromUri(fileUri: Uri): Bitmap?
    }

    interface Presenter {
        fun onScreenOpened(addFileCommentsArgument1: String, addFileCommentsArgument2: String)
        fun onSaveButtonClicked()
        fun onConsentSaveButtonClicked()
        fun onTextHasBeenChanged(editFileCommentText: String)
    }
}
