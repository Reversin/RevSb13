package com.example.revsb_11.presenters

import android.content.ContentResolver
import androidx.core.net.toUri
import com.example.revsb_11.contracts.ChangeFileNametContract
import com.example.revsb_11.data.WorkingWithFiles


class ChangeFileNamePresenter(
    private val view: ChangeFileNametContract.View,
    private val contentResolver: ContentResolver?
) : ChangeFileNametContract.Presenter {

    private lateinit var originalFile: String
    private lateinit var originalFileName: String
    private lateinit var fileFormat: String

    override fun onScreenOpened(changeFileNameArgument: String) {
        view.disableSaveButton()
        originalFile = changeFileNameArgument
        val fileUri = changeFileNameArgument.toUri()
        fileUri.let { selectedUri ->
            val file =
                contentResolver?.let { WorkingWithFiles().getFileNameFromUri(it, selectedUri) }
            fileFormat = ".${file?.fileType?.substringAfter("/")}"
            val indexFormat = file?.fileName?.lastIndexOf(dot)
            val fileName = indexFormat?.let { file.fileName.substring(0, it) }
            if (fileName != null) {
                originalFileName = fileName
                view.setText(fileName, fileFormat)
            }
        }
    }

    override fun textHasBeenChanged(editFileNameText: String) {
        if (editFileNameText != originalFileName) {
            view.enableSaveButton()
        } else {
            view.disableSaveButton()
        }
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