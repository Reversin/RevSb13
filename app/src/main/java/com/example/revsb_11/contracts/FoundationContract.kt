package com.example.revsb_11.contracts

interface FoundationContract {
    interface View {
        fun setLang(langKey: Int)
    }

    interface Presenter {
        fun onOptionLangSelected(langKey: Int)
    }
}