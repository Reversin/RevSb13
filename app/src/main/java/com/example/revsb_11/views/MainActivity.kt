package com.example.revsb_11.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.revsb_11.contracts.FoundationContract
import com.example.revsb_11.presenters.FoundationPresenter
import com.example.revsb_11.R
import com.example.revsb_11.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), FoundationContract.View {

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: FoundationContract.Presenter
    private lateinit var menu: Menu
    private val en = R.string.en
    private val ru = R.string.ru
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initPresenter()
        initNavListener()

    }

    private fun initPresenter() {
        presenter = FoundationPresenter(this)
    }

    private fun initNavListener() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.selectedFilesFragment,
            R.id.changeFileNameFragment
        ).build()
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Проверка текущего фрагмента и добавление кнопки в Toolbar при необходимости
            if (destination.id == R.id.changeFileNameFragment) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                // Другие операции, связанные с кнопкой Toolbar
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                // Другие операции, связанные с кнопкой Toolbar
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(filename: MenuItem): Boolean {
        return when (filename.itemId) {
            R.id.engLang -> {
                presenter.onOptionLangSelected(en)
                true
            }

            R.id.rusLang -> {
                presenter.onOptionLangSelected(ru)
                true
            }

            R.id.action_settings -> true
            else -> {
                super.onOptionsItemSelected(filename)
            }
        }
    }

    override fun setLang(langKey: Int) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(getString(langKey)))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}