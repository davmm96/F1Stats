package com.david.f1stats

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.david.f1stats.databinding.ActivityMainBinding
import com.david.f1stats.ui.settings.SettingsActivity
import com.david.f1stats.utils.MusicHelper
import com.david.f1stats.utils.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appToolbarConfiguration: AppBarConfiguration

    @Inject
    lateinit var preferencesManager: PreferencesHelper

    @Inject
    lateinit var musicManager: MusicHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initTheme()
        initMusic()
        initDefaultSeason()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }

    private fun initTheme() {
        AppCompatDelegate.setDefaultNightMode(preferencesManager.themeMode)
    }

    private fun initMusic() {
        if (preferencesManager.musicState) {
            musicManager.playMusic()
        }
    }

    private fun initDefaultSeason() {
        val season = preferencesManager.selectedSeason
        if (season.isNullOrEmpty()) {
            preferencesManager.selectedSeason = Calendar.getInstance().get(Calendar.YEAR).toString()
        }
    }

    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_races, R.id.navigation_ranking, R.id.navigation_circuits, R.id.navigation_favorites)
        )

        appToolbarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appToolbarConfiguration)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp(appToolbarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
