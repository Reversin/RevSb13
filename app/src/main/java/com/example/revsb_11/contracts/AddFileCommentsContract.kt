package com.example.revsb_11.contracts

import android.graphics.Bitmap
import android.net.Uri
import com.example.revsb_11.data.FileName

interface AddFileCommentsContract {

    interface View {
        fun setImageInImageView(imageUri: Uri)
        fun showAlertDialog()
        fun backToThePreviousFragmentWithChanges(originalFile: String, fileName: String)
        fun disableSaveButton()
        fun enableSaveButton()
        fun setFileNameHint(fileName: String)
        fun processingLinkToFile(fileUri: Uri): FileName?
        fun processingImageFile(fileUri: Uri): Bitmap
    }

    interface Presenter {
        fun onScreenOpened(changeFileNameArgument: String)
        fun onSaveButtonClicked()
        fun onConsentSaveButtonClicked(changedFileName: String)
        fun textHasBeenChanged(editFileNameText: String)
    }
}
