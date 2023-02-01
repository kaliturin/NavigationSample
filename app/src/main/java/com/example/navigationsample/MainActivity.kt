package com.example.navigationsample

import android.os.Bundle
import android.transition.Fade
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.navigationsample.databinding.ActivityMainBinding
import com.example.navigationsample.fragments.DashboardFragment
import com.example.navigationsample.fragments.HomeFragment
import com.example.navigationsample.fragments.NotificationsFragment
import com.example.navigationsample.fragments.SearchFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // init Timber
        Timber.plant(Timber.DebugTree())
        setSupportActionBar(binding.toolbar)
        initNavigation()
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.container)

        val screenIdsWithParentNavigation = setOf(
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.dashboardFragment,
            R.id.notificationsFragment
        )

        // this hides back icon in the toolbar of low-level screens
        val appBarConfiguration = AppBarConfiguration(screenIdsWithParentNavigation)

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNavigation.setupWithNavController(navController)

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

        // this helps to have the bottom menu icon be always checked correctly
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            item.isChecked = NavigationUI.onNavDestinationSelected(item, navController)
            item.isChecked
        }

        val defFragmentsWithParentNavigation = listOf(
            DialogFragment::class.java, HomeFragment::class.java,
            SearchFragment::class.java, DashboardFragment::class.java,
            NotificationsFragment::class.java
        )

        // hide parent's toolbar and bottom menu if a fragment has specific flags and isn't a dialog
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
            ) {
                val hasNav = defFragmentsWithParentNavigation.find { it.isInstance(f) } != null
                updateVisibility(binding.toolbar, WITH_PARENT_TOOLBAR, f, hasNav)
                updateVisibility(binding.bottomNavigation, WITH_PARENT_BOTTOM_MENU, f, hasNav)
            }
        }, true)

        // TODO: this is another way doing the same
//        // hide parent's toolbar and bottom menu if a fragment has specific flags and isn't a dialog
//        navController.addOnDestinationChangedListener { _, destination, args ->
//            val withParentNavigation = screenIdsWithParentNavigation.contains(destination.id) ||
//                    (destination is FloatingWindow)
//            binding.toolbar.isVisible = withParentNavigation ||
//                    args?.getBoolean(WITH_PARENT_TOOLBAR) == true
//            binding.bottomNavigation.isVisible = withParentNavigation ||
//                    args?.getBoolean(WITH_PARENT_BOTTOM_MENU) == true
//        }
    }

    private fun updateVisibility(
        view: ViewGroup, argName: String,
        fragment: Fragment, hasNavigation: Boolean,
    ) {
        val isVisible = hasNavigation ||
                fragment.arguments?.getBoolean(argName) == true
        if (view.isVisible != isVisible) {
            TransitionManager.beginDelayedTransition(view, Fade())
            view.isVisible = isVisible
        }
    }

    companion object {
        // parameters of a fragment that helps to show/hide parent's toolbar and bottom manu
        const val WITH_PARENT_TOOLBAR = "withParentToolbar"
        const val WITH_PARENT_BOTTOM_MENU = "withParentBottomMenu"
    }
}