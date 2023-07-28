package com.example.revsb_11.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.revsb_11.R
import com.example.revsb_11.databinding.ActivityMainBinding
import com.example.revsb_11.viewmodels.FoundationViewModel
import com.example.revsb_11.viewmodels.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var alertDialog: AlertDialog
    private lateinit var sharedViewModel: SharedViewModel
    private var flagShowConfirmation = false
    private val en = R.string.en
    private val ru = R.string.ru
    private val viewModel: FoundationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initSharedViewModel()
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initNavListener()
        initAlertDialog()
        changingValuesListeners()
    }

    private fun initSharedViewModel() {
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private fun initNavListener() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.selectedFilesFragment, R.id.addFileCommentsFragment
        ).build()
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.addFileCommentsFragment) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
    }

    private fun initAlertDialog() {
        alertDialog = AlertDialog.Builder(this).setTitle(R.string.change_file_name)
            .setMessage(R.string.exit_without_saving).setPositiveButton(R.string.yes) { _, _ ->
                viewModel.onBackToPreviousFragmentClicked()
            }.setNegativeButton(R.string.no) { _, _ ->

            }.setCancelable(false).create()
    }

    private fun changingValuesListeners() {
        viewModel.confirmationBeforeReturningLiveData.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                backToThePreviousFragment()
            }
        }
        viewModel.localizationIndexLiveData.observe(this) { localizationIndex ->
            changeLocalization(localizationIndex)
        }
        viewModel.onNavigateUpArrowClickedLiveData.observe(this) { event ->
            if (flagShowConfirmation) {
                event.getContentIfNotHandled()?.let {
                    showConfirmationOfTheChangesDialog()
                }
            } else {
                backToThePreviousFragment()
            }
        }
        sharedViewModel.editTextChangedLiveData.observe(this) { editTextChanged ->
            if (editTextChanged) {
                flagShowConfirmation = editTextChanged
            } else {
                flagShowConfirmation = editTextChanged
            }
        }
    }

    private fun backToThePreviousFragment() {
        navController.popBackStack(R.id.selectedFilesFragment, false)
        navController.navigate(R.id.selectedFilesFragment)
    }


    private fun showConfirmationOfTheChangesDialog() = alertDialog.show()


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(filename: MenuItem): Boolean {
        return when (filename.itemId) {
            android.R.id.home -> {
                viewModel.onNavigateUpArrowClicked()
                true
            }

            R.id.engLang -> {
                viewModel.onOptionLangSelected(en)
                true
            }

            R.id.rusLang -> {
                viewModel.onOptionLangSelected(ru)
                true
            }

            R.id.action_settings -> true
            else -> {
                super.onOptionsItemSelected(filename)
            }
        }
    }

    private fun changeLocalization(langKey: Int) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(getString(langKey)))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}