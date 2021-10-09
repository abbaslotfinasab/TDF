package com.utechia.tdf.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.utechia.tdf.R
import com.utechia.tdf.fragment.*
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
        val back: ConstraintLayout = findViewById(R.id.back)
        val name: TextView = findViewById(R.id.name)
        val title: TextView = findViewById(R.id.title)
        val subTitle: TextView =findViewById(R.id.subTitle)

        bottomNavigationView.visibility = View.VISIBLE
        itemNotification.isEnabled = true
        button.visibility = View.VISIBLE
        itemMenu.visibility = View.VISIBLE
        back.visibility = View.GONE
        name.visibility = View.GONE
        title.visibility = View.VISIBLE
        subTitle.visibility = View.VISIBLE


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
                .addToBackStack(null)
                .replace(navHostFragment.id,fragment)
                .commit()

        }

        button.setOnClickListener {

            val fragment:Fragment = QrCodeFragment()

            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(navHostFragment.id,fragment)
                .commit()

        }

        navigationView.setNavigationItemSelectedListener {

            when(it.itemId){

                R.id.surveySystemFragment -> {

                    val fragment:Fragment = SurveySystemFragment()

                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(navHostFragment.id,fragment)
                        .commit()

                    drawerLayout.closeDrawer(GravityCompat.START)

                    true
                }
                R.id.eventSystemFragment -> {

                    val fragment:Fragment = EventSystemFragment()

                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(navHostFragment.id,fragment)
                        .commit()

                    drawerLayout.closeDrawer(GravityCompat.START)

                    true
                }
                R.id.ticketSystemFragment -> {

                    val fragment:Fragment = TicketSystemFragment()

                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(navHostFragment.id,fragment)
                        .commit()

                    drawerLayout.closeDrawer(GravityCompat.START)

                    true
                }

                R.id.calendarFragment -> {

                    val fragment:Fragment = CalendarFragment()

                    supportFragmentManager
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(navHostFragment.id,fragment)
                        .commit()

                    drawerLayout.closeDrawer(GravityCompat.START)

                    true
                }
                R.id.exit -> {


                    Toast.makeText(this,"Log out",Toast.LENGTH_SHORT).show()


                    drawerLayout.closeDrawer(GravityCompat.START)

                    true
                }

                else -> {
                    false
            }



        }



        }

    }

}