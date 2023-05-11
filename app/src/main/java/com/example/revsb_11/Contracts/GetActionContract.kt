package com.example.revsb_11.Contracts


interface GetActionContract {
    interface FirstFragmentView {
        fun openFileSelector()
        fun setFileNameTitle(fileName: String)
        fun setLocale(langKey: String)
    }
    interface MainActivityView{
        fun setLang(langKey: String)
    }

    interface FirstFragmentPresenter {
        fun onScreenOpened()
        fun onBlaBlaButtonClicked()
        fun onFileNameSelected(text: String)

    }
    interface MainActivityPresenter {
        fun onOptionLangSelected(langKey: String)
    }

    interface Model {
        fun saveName(text: String)
        fun getFileName(): String?
        // +pass name
    }
//    interface Files {
//        fun takeFileNameDialog(firstFragmentPresenter: FirstFragmentPresenter, ActivityResultLauncher<String!>)
//
//
//    }

}