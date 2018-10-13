package com.getyourguide.berlintours

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.getyourguide.berlintours.common.extensions.resString
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.logger.Logger

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class MainActivity : AppCompatActivity() {

    private val navHost: NavHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NavigationUI.setupWithNavController(bottomNavigation, navHost.navController)

        bottomNavigation.findViewById<View>(R.id.discover).setOnClickListener { comingSoon() }
        bottomNavigation.findViewById<View>(R.id.bookings).setOnClickListener { comingSoon() }
        bottomNavigation.findViewById<View>(R.id.wish_list).setOnClickListener { comingSoon() }
        bottomNavigation.findViewById<View>(R.id.profile).setOnClickListener { comingSoon() }
    }

    override fun onSupportNavigateUp(): Boolean = navHost.navController.navigateUp() || super.onSupportNavigateUp()

    companion object {

        fun comingSoon() = Logger.snackbar(R.string.coming_soon.resString)
    }
}