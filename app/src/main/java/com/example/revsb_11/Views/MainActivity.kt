package com.example.revsb_11.Views

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.example.revsb_11.Contracts.FoundationContract
import com.example.revsb_11.Presenters.FoundationPresenter
import com.example.revsb_11.R
import com.example.revsb_11.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), FoundationContract.View {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: FoundationContract.Presenter
    private lateinit var menu: Menu


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

    private fun changeMenuItemColor(itemId: Int, color: Int) {
        val menuItem = menu.findItem(itemId)
        menuItem.icon?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.engLang -> {

                item.icon?.setColorFilter(
                    ContextCompat.getColor(this, R.color.teal_700),
                    PorterDuff.Mode.SRC_IN
                )
                presenter.onOptionLangSelected("en")

                invalidateOptionsMenu()
                true
            }

            R.id.rusLang -> {
                changeMenuItemColor(R.id.engLang, Color.BLUE)
                presenter.onOptionLangSelected("ru")
                true
            }

            R.id.action_settings -> true
            else -> {

                super.onOptionsItemSelected(item)
            }
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