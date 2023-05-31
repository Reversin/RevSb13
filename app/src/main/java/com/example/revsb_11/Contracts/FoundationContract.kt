package com.example.revsb_11.Contracts

interface FoundationContract {
    interface View {
        fun setLang(langKey: Int)
    }

    interface Presenter {
        fun onOptionLangSelected(langKey: Int)
    }
}