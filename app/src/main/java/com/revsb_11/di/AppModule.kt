package com.revsb_11.di

import android.content.ContentResolver
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.revsb_11.models.SelectedFilesModel
import com.revsb_11.repository.DriveRepository
import com.revsb_11.utils.ExtractFileDetails
import com.revsb_11.viewmodels.AddFileCommentsViewModel
import com.revsb_11.viewmodels.FoundationViewModel
import com.revsb_11.viewmodels.SelectedFilesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
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

    single<Gson> {
        Gson()
    }

    single { DriveRepository(androidContext()) }

    single {
        val sharedPrefs = get<SharedPreferences>()
        val gson = get<Gson>()
        SelectedFilesModel(sharedPrefs, gson)
    }

    viewModel {
        val contentResolver = androidApplication().contentResolver
        val selectedFilesModel = get<SelectedFilesModel>()
        val fileExtractor = get<ExtractFileDetails> { parametersOf(contentResolver) }
        val driveRepository = get<DriveRepository> { parametersOf(contentResolver) }
        SelectedFilesViewModel(selectedFilesModel, fileExtractor, driveRepository)
    }

    viewModel {
        val contentResolver = androidApplication().contentResolver
        val fileExtractor = get<ExtractFileDetails> { parametersOf(contentResolver) }
        val sharedPrefs = get<SharedPreferences>()
        AddFileCommentsViewModel(fileExtractor, sharedPrefs)
    }

    viewModel { FoundationViewModel() }
}