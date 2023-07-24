package com.example.revsb_11.viewmodelsfactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.revsb_11.data.WorkingWithFiles
import com.example.revsb_11.viewmodels.AddFileCommentsViewModel
import java.lang.IllegalArgumentException

class AddFileCommentsViewModelFactory(private val workingWithFiles: WorkingWithFiles) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddFileCommentsViewModel(workingWithFiles) as T
    }
}