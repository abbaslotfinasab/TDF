package com.utechia.tdf.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.utechia.tdf.R
import com.utechia.tdf.fragment.NotificationFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView:NavigationView = findViewById(R.id.navigationView)
        val drawerLayout:DrawerLayout = findViewById(R.id.drawer_layout)
        val itemMenu:ImageView = findViewById(R.id.menu)
        val itemNotification:ImageView = findViewById(R.id.notification)
        val button:ImageButton = findViewById(R.id.customButton)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)

        button.bringToFront()


        itemMenu.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }

        itemNotification.setOnClickListener {

            val fragment:Fragment = NotificationFragment()

            supportFragmentManager
                .beginTransaction()
                .replace(navHostFragment.id,fragment)
                .commit()

        }

    }

}