package com.example.navigationsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.navigationsample.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setSupportActionBar(binding.toolbar)
        initNavigation()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.container)

        // this hides back icon in the toolbar of low-level screens
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.dashboardFragment,
                R.id.notificationsFragment
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

        // this helps to have items menu checked when in the low-lever menu's fragment there is content fragment
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            item.isChecked = NavigationUI.onNavDestinationSelected(item, navController)
            item.isChecked
        }

        // toolbar's on back push listener
        binding.toolbar.setNavigationOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.searchFragment,
                R.id.dashboardFragment,
                R.id.notificationsFragment -> {
                    if (onBackPressedDispatcher.hasEnabledCallbacks())
                        onBackPressedDispatcher.onBackPressed()
                    else
                        navController.navigateUp()
                }
                else -> navController.navigateUp()
            }
        }
    }
}