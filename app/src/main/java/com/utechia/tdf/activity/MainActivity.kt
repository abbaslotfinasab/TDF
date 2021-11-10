package com.utechia.tdf.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.utechia.tdf.R
import com.utechia.tdf.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = getSharedPreferences("tdf", Context.MODE_PRIVATE)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)

        if (pref.getString("user_id",null) ==  null){

            graph.setStartDestination(R.id.loginFragment)

                }
                else
                {
                    graph.setStartDestination(R.id.homeFragment)
                }
        navHostFragment.navController.graph = graph

        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.bottomNavigation.setupWithNavController(navController)

           when (destination.id){

                R.id.homeFragment ->{
                    design(0)

                }
                R.id.teaBoyFragment ->{
                    design(0)

                }
                R.id.reservationFragment ->{
                    design(0)

                }
                R.id.profileFragment ->{
                    design(0)

                }
                R.id.qrCodeFragment -> {
                    design(0)
                }
               R.id.loginFragment ->{
                   design(1)

               }

               R.id.waitingFragment -> {
                   design(1)
               }

               R.id.authenticationFragment -> {
                   design(1)
               }

                else -> {
                    design(2)
                }
        }

        }

        binding.bottomNavigation.setupWithNavController(navController)

        binding.menu.setOnClickListener {

            binding.drawerLayout.openDrawer(GravityCompat.END)

        }

        binding.customButton.apply {

            this.bringToFront()

            setOnClickListener {

                navController.navigate(R.id.qrCodeFragment)

            }
        }


        binding.navigationView.setNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.surveySystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.surveySystemFragment)

                    }, 50)

                    true
                }
                R.id.eventSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.eventSystemFragment)

                    }, 50)

                    true
                }
                R.id.ticketSystemFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.ticketSystemFragment)

                    }, 50)


                    true
                }

                R.id.calendarFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.calendarFragment)

                    }, 50)


                    true
                }

                R.id.permissionFragment -> {

                    Handler(Looper.getMainLooper()).postDelayed({

                        navController.navigateUp()
                        navController.navigate(R.id.permissionFragment)

                    }, 50)

                    true
                }

                else -> {
                    false
                }

            }

        }

        binding.exit.setOnClickListener {


            pref.edit().remove("user_id").remove("user_token").apply()
            navController.navigateUp()
            navController.navigate(R.id.loginFragment)

        }

    }

    private fun design(position:Int){

        when(position){

            0 ->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

            }

            1->{
                window.statusBarColor = ContextCompat.getColor(this, R.color.white)
                binding.toolbar.visibility = View.GONE
                binding.customButton.visibility = View.GONE
                binding.bottomNavigation.visibility = View.GONE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

            }

            2->{
                window.statusBarColor = ContextCompat.getColor(this,R.color.status)
                binding.toolbar.visibility = View.VISIBLE
                binding.customButton.visibility = View.VISIBLE
                binding.bottomNavigation.visibility = View.VISIBLE
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                binding.drawerLayout.closeDrawer(GravityCompat.END)

            }

        }

    }

}