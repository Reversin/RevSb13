package com.example.revsb_11.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.revsb_11.contracts.FoundationContract
import com.example.revsb_11.presenters.FoundationPresenter
import com.example.revsb_11.R
import com.example.revsb_11.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), FoundationContract.View {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: FoundationContract.Presenter
    private lateinit var menu: Menu
    private val en = R.string.en
    private val ru = R.string.ru


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        presenter = FoundationPresenter(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun setLang(langKey: Int) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(getString(langKey)))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}