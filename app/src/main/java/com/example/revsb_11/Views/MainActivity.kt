package com.example.revsb_11.Views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.revsb_11.Contracts.GetActionContract
import com.example.revsb_11.Presenters.FirstFragmentPresenter
import com.example.revsb_11.Presenters.MainActivityPresenter
import com.example.revsb_11.R
import com.example.revsb_11.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), GetActionContract.MainActivityView {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: GetActionContract.MainActivityPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        presenter = MainActivityPresenter(this)

//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.engLang -> {
                presenter.onOptionLangSelected("en")
                true
            }
            R.id.rusLang -> {
                presenter.onOptionLangSelected("ru")
                true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
            //test git
        }
    }

    override fun setLang(langKey: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(langKey))
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}