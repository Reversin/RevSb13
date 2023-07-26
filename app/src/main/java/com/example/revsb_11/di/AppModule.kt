package com.example.revsb_11.di

import android.content.ContentResolver
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.revsb_11.models.SelectedFilesModel
import com.example.revsb_11.utils.WorkingWithFiles
import com.example.revsb_11.viewmodels.AddFileCommentsViewModel
import com.example.revsb_11.viewmodels.FoundationViewModel
import com.example.revsb_11.viewmodels.SelectedFilesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf

import org.koin.dsl.module

val appModule = module {

    factory { (contentResolver: ContentResolver) -> WorkingWithFiles(contentResolver) }

    single<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(get()) }

    single { SelectedFilesModel(get()) }

    viewModel {
        val contentResolver = androidApplication().contentResolver
        SelectedFilesViewModel(get(), get { parametersOf(contentResolver) })
    }
    viewModel {
        val contentResolver = androidApplication().contentResolver
        AddFileCommentsViewModel( get { parametersOf(contentResolver) })
    }
    viewModel { FoundationViewModel() }
}