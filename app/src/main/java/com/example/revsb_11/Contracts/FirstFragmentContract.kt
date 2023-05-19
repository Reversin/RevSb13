package com.example.revsb_11.Contracts


interface FirstFragmentContract {
    interface View {
        fun openFileSelector()
        fun setFileNameTitle(filePath: String)
        fun setLocale(langKey: String)
    }
    interface Presenter {
        fun onScreenOpened()
        fun onBlaBlaButtonClicked()
        fun onFileNameSelected(text: String)

    }
    interface Model {
        fun saveName(text: String)
        fun getFileName(): String?
        // +pass name
    }
}
