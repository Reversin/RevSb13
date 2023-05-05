package com.example.revsb_11.Contracts


interface GetActionContract {
    interface View {
        fun takeFileNameDialog()
        fun editTVFileName(text: String, key: Boolean)
        fun setLocale(langKey: String)
    }

    interface Presenter {
        fun recoveryLastFileName()
        fun buttonFileDialogReaction()
        fun processFileName(text: String)
        fun changeLocalization(langKey: String)
    }

    interface Model {
        fun saveNameinModel(text: String)
        fun extractNameFromModel(): String?// context
        // +pass name
    }
//    interface Files {
//        fun takeFileNameDialog(firstFragmentPresenter: FirstFragmentPresenter, ActivityResultLauncher<String!>)
//
//
//    }

}