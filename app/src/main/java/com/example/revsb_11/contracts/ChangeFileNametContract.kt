package com.example.revsb_11.contracts

interface ChangeFileNametContract {

    interface View {
        fun setText(fileName: String, fileFormat: String)
        fun showAlertDialog()
        fun backToThePreviousFragmentWithChanges(originalFile: String, fileName: String)
        fun disableSaveButton()
        fun enableSaveButton()
    }

    interface Presenter {
        fun onScreenOpened(changeFileNameArgument: String)
        fun onSaveButtonClicked()
        fun onConsentSaveButtonClicked(changedFileName: String)
        fun textHasBeenChanged(editFileNameText: String)
    }
}
