package com.example.revsb_11.di

import android.content.ContentResolver
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.example.revsb_11.models.SelectedFilesModel
import com.example.revsb_11.utils.ExtractFileDetails
import com.example.revsb_11.viewmodels.AddFileCommentsViewModel
import com.example.revsb_11.viewmodels.FoundationViewModel
import com.example.revsb_11.viewmodels.SelectedFilesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module

val appModule = module {


    factory { (contentResolver: ContentResolver) ->
        ExtractFileDetails(contentResolver)
    }

    single<SharedPreferences> {
        val appContext = get<android.app.Application>().applicationContext
        PreferenceManager.getDefaultSharedPreferences(appContext)
    }

    single {
        val sharedPrefs = get<SharedPreferences>()
        SelectedFilesModel(sharedPrefs)
    }

    viewModel {
        val contentResolver = androidApplication().contentResolver
        val selectedFilesModel = get<SelectedFilesModel>()
        val fileExtractor = get<ExtractFileDetails> { parametersOf(contentResolver) }
        SelectedFilesViewModel(selectedFilesModel, fileExtractor)
    }

    viewModel {
        val contentResolver = androidApplication().contentResolver
        val fileExtractor = get<ExtractFileDetails> { parametersOf(contentResolver) }
        AddFileCommentsViewModel(fileExtractor)
    }

    viewModel { FoundationViewModel() }
}