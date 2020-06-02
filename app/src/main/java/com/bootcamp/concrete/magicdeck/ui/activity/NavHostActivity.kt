package com.bootcamp.concrete.magicdeck.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bootcamp.concrete.magicdeck.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavHostActivity : AppCompatActivity(R.layout.activity_nav_host) {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController(R.id.nav_host_fragment_container)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        setUpFields(navView)
    }

    private fun setUpFields(navView: BottomNavigationView) {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_catalog, R.id.navigation_favorites
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_carousel -> {
                    navView.isVisible = false
                }
                else -> {
                    navView.isVisible = true
                }
            }
        }
        navView.setupWithNavController(navController)
    }

}