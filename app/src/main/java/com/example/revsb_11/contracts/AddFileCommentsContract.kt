package com.example.revsb_11.contracts

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri

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
        fun processingLinkToFile(fileUri: Uri): String?
        fun processingImageFile(fileUri: Uri): Bitmap
        fun getBitmapImageFromUri(fileUri: Uri): Bitmap?
    }

    interface ViewModel {
        fun processFirstArgument(fileName: String)
        fun processSecondArgument(fileComment: String)
        fun saveFileImage(fileImage: Bitmap)
        fun onTextHasBeenChanged(changedComment: String)
        fun onSaveButtonClicked()
        fun onConsentSaveButtonClicked()
    }
}
